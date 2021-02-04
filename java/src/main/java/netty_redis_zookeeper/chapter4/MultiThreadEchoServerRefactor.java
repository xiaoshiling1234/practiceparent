package netty_redis_zookeeper.chapter4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * （1）引入多个选择器。
 * （2）设计一个新的子反应器（SubReactor）类，一个子反应器负责查询一个选择器。
 * （3）开启多个反应器的处理线程，一个线程负责执行一个子反应器（SubReactor）。
 */
public class MultiThreadEchoServerRefactor {
    ServerSocketChannel serverSocket;
    AtomicInteger next=new AtomicInteger(0);
    //选择器集合，引入多个选择器
    Selector[] selectors=new Selector[2];
    //引入多个子反应器
    SubReactor[] subSelectors=null;

    public MultiThreadEchoServerRefactor() throws IOException {
        //初始化多个选择器
        selectors[0]=Selector.open();
        selectors[1]=Selector.open();
        serverSocket= ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);
        serverSocket.socket().bind(address);
        //非阻塞
        serverSocket.configureBlocking(false);
        //第一个选择器，负责监控新连接事件
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        //绑定handler
        sk.attach(new AcceptorHandler());
        //第一个子反应器，一个子反应器负责一个选择器
        SubReactor subReactor1 = new SubReactor(selectors[0]);
        SubReactor subReactor2 = new SubReactor(selectors[1]);
        subSelectors=new SubReactor[]{subReactor1,subReactor2};
    }
    private void startService(){
        new Thread(subSelectors[0]).start();
        new Thread(subSelectors[1]).start();
    }

    //子反应器
    class SubReactor implements Runnable{
        //每个线程负责一个选择器的查询和选择
        final Selector selector;

        public SubReactor(Selector selector) {
            this.selector = selector;
        }
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        //反应器负责dispatch收到的事件
                        SelectionKey sk = it.next();
                        dispatch(sk);
                    }
                    selected.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void dispatch(SelectionKey sk) {
            Runnable handler = (Runnable) (sk.attachment());
            //调用之前绑定到选择键的handler处理器对象
            if (handler != null) {
                //并没有启动新的线程
                handler.run();
            }
        }
    }

    //Handler:新连接处理器
    private class AcceptorHandler implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel!=null){
                    new MultiThreadEchoHandler(selectors[next.get()],channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (next.incrementAndGet()==selectors.length){
                next.set(0);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MultiThreadEchoServerRefactor server = new MultiThreadEchoServerRefactor();
        server.startService();
    }


}
