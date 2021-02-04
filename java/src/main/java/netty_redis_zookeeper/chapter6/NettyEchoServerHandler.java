package netty_redis_zookeeper.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j;

/**
 * 这里的NettyEchoServerHandler在前面加了一个特殊的Netty注解：
 * @ChannelHandler.Sharable。
 * 这个注解的作用是标注一个Handler实例可以被
 * 多个通道安全地共享。
 * 什么叫作Handler共享呢？就是多个通道的流水线可以加入同一个
 * Handler业务处理器实例。而这种操作，Netty默认是不允许的。
 */
@Log4j
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {
    public static final ChannelHandler INSTANCE = new NettyEchoServerHandler();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("msg type: "+(in.hasArray()?"堆内存":"直接内存"));
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0,arr);
        log.info("server receive:"+new String(arr,"UTF-8"));
        log.info("写回前,msg.refCnt::"+((ByteBuf)msg).refCnt());
        //写回数据，异步任务
        ChannelFuture f = ctx.writeAndFlush(msg);
        f.addListener((ChannelFuture  futureListener)->{
            log.info("写回后,msg.refCnt:"+((ByteBuf) msg).refCnt());
        });
    }
}
