package netty_redis_zookeeper.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

@Log4j
public class ReferenceTest {
    @Test
    public void testRef(){
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        log.info("after create:"+buffer.refCnt());
        buffer.retain();
        log.info("after retain:"+buffer.refCnt());
        buffer.release();
        log.info("after release:"+buffer.refCnt());
        buffer.release();
        log.info("after release:"+buffer.refCnt());
        //错误：refCnt:0不能再retain
        buffer.retain();
        log.info("after retain:"+buffer.refCnt());
    }
}
