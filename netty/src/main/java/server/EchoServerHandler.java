package server;

import encoder.Header;
import encoder.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    //接收请求后的处理类
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message msg1=(Message)msg;
        System.out.println(msg1.getData());
        //此处写接收到客户端请求后的业务逻辑
        String content="hello world,this is nettyServer";
        Header header=new Header((byte)0, (byte)1, (byte)1, (byte)1, (byte)0, "713f17ca614361fb257dc6741332caf2",content.getBytes("UTF-8").length, 1);
        Message message=new Message(header,content);
        ctx.writeAndFlush(message);
        //ctx.writeAndFlush(Unpooled.copiedBuffer("hello world,this is nettyServer",CharsetUtil.UTF_8));
    }
    //读取完成后处理方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler.channelReadComplete");
        //ctx.flush();
    }
    //异常捕获处理方法

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
