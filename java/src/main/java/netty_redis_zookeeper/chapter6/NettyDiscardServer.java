package netty_redis_zookeeper.chapter6;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyDiscardServer {
    private final int serverPort;
    //Netty的服务启动类ServerBootstrap，它的职责是一个组装和集成器，
    // 将不同的Netty组件组装在一起
    //另外，ServerBootstrap能够按照应用场景的需要，为组件设置好对应的参数，
    // 最后实现Netty服务器的监听和启动
    ServerBootstrap b=new ServerBootstrap();

    public NettyDiscardServer(int serverPort) {
        this.serverPort = serverPort;
    }
    public void runServer(){
        //创建反应器线程组
        //包工头，主要负责新连接IO的监听
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        //工人，主要负责传输通道的IO事件的处理
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            //1设置反应器线程组
            b.group(bossLoopGroup,workerLoopGroup);
            //2设置nio类型的通道
            b.channel(NioServerSocketChannel.class);
            //3设置监听端口
            b.localAddress(serverPort);
            //4设置通道的参数
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //5装配子通道流水线
            b.childHandler(new ChannelInitializer<SocketChannel>(){
                //有连接到达时会创建一个通道
                protected void initChannel(SocketChannel ch)throws Exception{
                    //流水线管理子通道中的Handler处理器
                    //向子通道流水线添加一个handler处理器
                    //Handler处理器的作用是对应到IO事件，实现IO事件的业务处理。
                    ch.pipeline().addLast(new NettyDiscardHandler());
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //8关闭EventLoopGroup
            //释放掉所有资源包括创建的线程
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port=9999;
        new NettyDiscardServer(port).runServer();
    }
}
