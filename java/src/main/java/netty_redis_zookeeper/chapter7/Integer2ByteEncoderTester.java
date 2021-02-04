package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class Integer2ByteEncoderTester {
    /**
     * 测试整数编码器
     */
    @Test
    public void testIntegerToByteDecoder(){
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j=0;j<100;j++){
            channel.write(j);
        }
        channel.flush();
        //取得通道的出站数据包
        ByteBuf buf = (ByteBuf) channel.readOutbound();
        while (null!=buf){
            System.out.println("o="+buf.readInt());
            buf=(ByteBuf) channel.readOutbound();
        }
        try{
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
