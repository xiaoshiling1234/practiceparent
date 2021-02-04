package netty_redis_zookeeper.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Status> {
    enum Status{
        PARSE_1,PARSE_2
    }
    private int first;
    private int second;

    public IntegerAddDecoder() {
        //初始化父类的state属性，表示当前阶段
        super(Status.PARSE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()){
            case PARSE_1:
                //从装饰器ByteBuf中读取数据
                first=in.readInt();
                //从第一步解析成功，
                    //进入第二步，并设置“读断点指针”为当前的读取位置
                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                second=in.readInt();
                Integer sum = first + second;
                out.add(sum);
                checkpoint(Status.PARSE_1);
                break;
            default:
                break;
        }
    }
}
