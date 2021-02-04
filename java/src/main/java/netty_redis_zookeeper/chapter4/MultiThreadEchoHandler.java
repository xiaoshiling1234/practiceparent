package netty_redis_zookeeper.chapter4;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MultiThreadEchoHandler。主要的升级是引入了一个线程池（ThreadPool），
 * 业务处理的代码执行在自己的线程池中，彻底地做到业务处理线程和反应器IO事
 * 件线程的完全隔离
 */
public class MultiThreadEchoHandler implements Runnable{
    final SocketChannel channel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
    static final int RECEIVING=0,SENDING=1;
    int state=RECEIVING;
    //引入线程池
    static ExecutorService pool= Executors.newFixedThreadPool(4);
    public MultiThreadEchoHandler(Selector selector, SocketChannel c) throws IOException {
        channel=c;
        c.configureBlocking(false);
        //取得选择键，再设置感兴趣的IO事件
        sk = channel.register(selector, 0);
        //将自己作为sk附件
        sk.attach(this);
        //向sk选择键注册read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }
    @Override
    public void run() {
        pool.execute(new AsyncTask());
    }

    private class AsyncTask implements Runnable {
        @Override
        public void run() {
            MultiThreadEchoHandler.this.asyncRun();
        }
    }

    //业务处理不再反应器线程中执行
    private void asyncRun() {
        try {
            if (state==SENDING){
                //写入通道
                channel.write(byteBuffer);
                byteBuffer.clear();
                sk.interestOps(SelectionKey.OP_READ);
                state=RECEIVING;
            }else if (state==RECEIVING){
                int length=0;
                while ((length=channel.read(byteBuffer))>0){
                    System.out.println(new String(byteBuffer.array(),0,length));
                }
                byteBuffer.flip();
                sk.interestOps(SelectionKey.OP_WRITE);
                state=SENDING;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
