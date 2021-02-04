package netty_redis_zookeeper.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class String2IntegerEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        char[] array = msg.toCharArray();
        for (char a:array){
            //48是0的编码,57是9的编码
            if (a>=48&&a<57){
                out.add(new Integer(a));
            }
        }
    }
}
