package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;

//LineBasedFrameDecoder的使用
public class NettyOpenBoxDecoder {
    static String spliter="\r\n";
    static String spliter2="\r\n";
    public static final int VERSION=100;
    static String content="疯狂创客圈：高性能学习社群！";

    @Test
    public void testLineBasedFrameDecoder(){
        try {
            ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            for (int j=0;j<100;j++){
                //1-3之间的随机数
                int random = new Random().nextInt(4);
                ByteBuf buf = Unpooled.buffer();
                for (int k=0;k<random;k++){
                    buf.writeBytes(content.getBytes("utf-8"));
                }
                buf.writeBytes(spliter.getBytes("utf-8"));
                channel.writeInbound(buf);
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelimiterBasedFrameDecoder(){
        try {
            final ByteBuf delimiter=Unpooled.copiedBuffer(spliter2.getBytes("utf-8"));
            ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,true,delimiter));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            for (int j=0;j<100;j++){
                //1-3之间的随机数
                int random = new Random().nextInt(4);
                ByteBuf buf = Unpooled.buffer();
                for (int k=0;k<random;k++){
                    buf.writeBytes(content.getBytes("utf-8"));
                }
                buf.writeBytes(spliter2.getBytes("utf-8"));
                channel.writeInbound(buf);
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder1(){
        try {
            LengthFieldBasedFrameDecoder spliter = new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4);
            ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            for (int j=1;j<100;j++){
                ByteBuf buf = Unpooled.buffer();
                String s=j+"次发送->"+content;
                byte[] bytes = s.getBytes("utf-8");
                buf.writeInt(bytes.length);
                buf.writeBytes(bytes);
                channel.writeInbound(buf);
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder2(){
        try {
            LengthFieldBasedFrameDecoder spliter = new LengthFieldBasedFrameDecoder(1024, 0, 4, 2, 6);
            ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            for (int j=1;j<100;j++){
                ByteBuf buf = Unpooled.buffer();
                String s=j+"次发送->"+content;
                byte[] bytes = s.getBytes("utf-8");
                buf.writeInt(bytes.length);
                buf.writeChar(VERSION);
                buf.writeBytes(bytes);
                channel.writeInbound(buf);
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }
        }
}
