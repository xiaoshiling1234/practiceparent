package netty_redis_zookeeper.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j;

import java.util.logging.Logger;

@Log4j
public class StringProcessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String s=(String) msg;
        log.info("打印出一个字符串："+s);
    }
}
