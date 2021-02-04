package netty_redis_zookeeper.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

@Log4j
public class WriteReadTest {
    @Test
    public void testWritRead(){
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        System.out.println("动作：分配ByteBuf(9.100)"+buffer);
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println("动作：写入四个字节(1，2，3，4)"+buffer);
        log.info("start===============:get=============");
        getByteBuf(buffer);
        System.out.println("动作：取数据ByteBuf"+buffer);
        log.info("start===============:read=============");
        readByteBuf(buffer);
        System.out.println("动作：读完ByteBuf"+buffer);
    }

    private void readByteBuf(ByteBuf buffer) {
        while (buffer.isReadable()){
            log.info("取一个字节："+buffer.readByte());
        }
    }

    private void getByteBuf(ByteBuf buffer) {
        for (int i=0;i<buffer.readableBytes();i++){
            log.info("读一个字节："+buffer.getByte(i));
        }
    }
}
