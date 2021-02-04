package netty_redis_zookeeper.chapter7;

import io.netty.channel.CombinedChannelDuplexHandler;

public class IntegerDuplexHandler extends CombinedChannelDuplexHandler<Byte2IntegerDecoder,Integer2ByteEncoder> {
    public IntegerDuplexHandler() {
        super(new Byte2IntegerDecoder(),new Integer2ByteEncoder());
    }
}
