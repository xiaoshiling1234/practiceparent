package netty_redis_zookeeper.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class InHandlerDemoTester {
    @Test
    public void testInHandlerLifeCircle(){
        InHandlerDemo inHandler = new InHandlerDemo();
        //初始化处理器
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(inHandler);
            }
        };
        //创建嵌入式通道
        EmbeddedChannel channel = new EmbeddedChannel(i);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        //模拟入站，写一个入站数据包
        channel.writeInbound(buffer);
        channel.flush();
        //模拟入站，再写一个入站数据包
        channel.writeInbound(buffer);
        channel.flush();
        //通道关闭
        channel.close();
        try{
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
