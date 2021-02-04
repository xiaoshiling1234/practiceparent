package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Random;

public class StringReplayDecoderTester {
    static String content="疯狂创客圈：高性能学习社群";
    @Test
    public void testStringReplayDecoder(){
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new StringReplayDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));
        for (int j=0;j<100;j++){
            //1-3之间的随机数
            int random = new Random().nextInt(4);
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(bytes.length*random);
            System.out.println(random);
            for (int k=0;k<random;k++){
                buf.writeBytes(bytes);
            }
            channel.writeInbound(buf);
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
