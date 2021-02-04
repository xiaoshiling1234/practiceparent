package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.Status> {
    enum Status{
        PARSE_1,PARSE_2
    }
    private int length;
    private byte[] inBytes;

    public StringReplayDecoder() {
        super(Status.PARSE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()){
            case PARSE_1:
                //第一步，从装饰器ByteBuf中读取长度
                length=in.readInt();
                inBytes=new byte[length];
                //第二步，读取内容
                    //并且设置“读断点指针”为当前的读取位置，同事设置下一个阶段
                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                //第一步，从装饰器ByteBuf中读取内容数组
                in.readBytes(inBytes,0,length);
                out.add(new String(inBytes,"UTF-8"));
                //第二步解析成功
                    //进入第一步，读取下一个字符创的长度
                    //并且设置“读断点指针”为当前的读取位置，同时设置下一个阶段
                checkpoint(Status.PARSE_1);
                break;
            default:
                break;
        }
    }
}
