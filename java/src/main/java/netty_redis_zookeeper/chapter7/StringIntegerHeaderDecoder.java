package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class StringIntegerHeaderDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //可读字节小于4，消息头还没读满，返回
        if (in.readableBytes() < 4) {
            return;
        }
        //消息头已经完整
        //在真正开始从缓冲区读取数据之前，调用markReaderIndex()设置回滚点
        //回滚点为消息头的readIndex读指针位置
        in.markReaderIndex();
        int length = in.readInt();
        //从缓冲区中读出消息头的大小，这会使得readIndex读指针前移
        //剩余长度不够消息体，重置读指针
        if (in.readableBytes() < length) {
            //读指针回滚到消息头的readIndex位置处，未进行状态的保存
            in.resetReaderIndex();
            return;
        }
        //读取数据，编码成字符串
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        out.add(new String(bytes, "UTF-8"));
    }
}
