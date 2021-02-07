package netty_redis_zookeeper.chapter8;

import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class JsonSendClient {
    static String content="疯狂创客圈：高性能学习社群！";
    private int serverPort;
    private String serverIp;
    Bootstrap b=new Bootstrap();

    public JsonSendClient(int serverPort, String serverIp) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }
    public void runClient(){
        //创建反应器线程组
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            b.group(workerLoopGroup);
            b.channel(NioSocketChannel.class);
            b.remoteAddress(serverIp,serverPort);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                }
            });
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture futureListener)->{
                if (futureListener.isSuccess()){
                    System.out.println("EchoClient客户端连接成功！");
                }else {
                    System.out.println("EchoClient客户端连接失败！");
                }
            });
            f.sync();
            Channel channel = f.channel();
            for (int i=0;i<1000;i++){
                JsonMsg user=build(i,i+"->"+content);
                channel.writeAndFlush(user.convertToJson());
                System.out.println("发送报文:"+user.convertToJson());
            }
            channel.flush();
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    private JsonMsg build(int id, String content) {
        JsonMsg user = new JsonMsg();
        user.setId(id);
        user.setContent(content);
        return user;
    }

    public static void main(String[] args) {
        new JsonSendClient(9999,"localhost").runClient();
    }
}
