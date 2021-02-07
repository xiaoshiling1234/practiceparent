package netty_redis_zookeeper.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.scene.chart.Chart;
import netty_redis_zookeeper.chapter6.NettyEchoClientHandler;

import javax.swing.table.TableCellRenderer;
import java.nio.charset.Charset;

public class NettyDumpSendClient {
    private int serverPort;
    private String serverIp;
    Bootstrap b=new Bootstrap();

    public NettyDumpSendClient(int serverPort, String serverIp) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }
    public void runClient(){
        try {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            b.group(eventLoopGroup);
            b.channel(NioSocketChannel.class);
            b.remoteAddress(serverIp, serverPort);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture futureListener) -> {
                if (futureListener.isSuccess()) {
                    System.out.println("EchoClient客户端连接成功！");
                } else {
                    System.out.println("EchoClient客户端连接失败！");
                }
            });
            f.sync();
            Channel channel = f.channel();
            //发送大量的文字
            String content="何以解忧，唯有嘿咻！\n";
            byte[] bytes = content.getBytes(Charset.forName("utf-8"));
            for (int i=0;i<100;i++){
                //发送ByteBuf
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port=9999;
        String ip ="localhost";
        new NettyDumpSendClient(port,ip).runClient();
    }
}
