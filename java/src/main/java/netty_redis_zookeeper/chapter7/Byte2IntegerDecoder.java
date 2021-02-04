package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.logging.Logger;

@Log4j
public class Byte2IntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes()>4){
            int i = in.readInt();
            log.info("解码出一个整数："+i);
            out.add(i);
        }
    }
}
