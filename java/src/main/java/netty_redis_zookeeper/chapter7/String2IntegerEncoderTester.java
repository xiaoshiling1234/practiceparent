package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class String2IntegerEncoderTester {

    @Test
    public void testStringToIntegerDecoder(){
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j=0;j<100;j++){
            String s="i am"+j;
            channel.write(s);
        }
        channel.flush();
        ByteBuf buf = (ByteBuf) channel.readOutbound();
        while (null!=buf){
            System.out.println("o="+buf.readInt());
            buf=(ByteBuf) channel.readOutbound();
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
