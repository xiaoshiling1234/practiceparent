package netty_redis_zookeeper.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

public class JsonServer {
    public void runServer(){
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            //创建反应器线程组
            ServerBootstrap b = new ServerBootstrap();
            //1设置反应器线程组
            b.group(bossLoopGroup,workerLoopGroup);
            //2设置nio类型的通道
            b.channel(NioServerSocketChannel.class);
            //3设置监听端口
            int serverPort=9999;
            b.localAddress(serverPort);
            //4设置通道的参数
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //5.装配子通道流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
                    ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    ch.pipeline().addLast(new JsonMsgDecoder());
                }
            });

            //6开始绑定服务器
            //通过调用sync同步方法阻塞知道绑定成功
            ChannelFuture channelFuture = b.bind().sync();
            System.out.println("服务器启动成功，监听端口"+channelFuture.channel().localAddress());
            //7等待通道关闭的异步任务结束
            //服务监听通达会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static class JsonMsgDecoder extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String json = (String) msg;
            JsonMsg jsonMsg = JsonMsg.parseFromJson(json);
            System.out.println("收到一个Json数据包=》"+jsonMsg);
        }
    }

    public static void main(String[] args) {
        new JsonServer().runServer();
    }
}
