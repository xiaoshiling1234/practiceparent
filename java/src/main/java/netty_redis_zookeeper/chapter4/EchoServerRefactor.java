package netty_redis_zookeeper.chapter4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * EchoServer回显服务器的功能很简单：读取客户端的输入，
 * 回显到客户端，所以也叫回显服务器。
 * 基于Reactor反应器模式来实现，设计3个重要的类：
 * （1）设计一个反应器类：EchoServerReactor类。
 * （2）设计两个处理器类：AcceptorHandler新连接处理器、EchoHandler回显处理器。
 */
public class EchoServerRefactor implements Runnable {
    Selector selector;
    ServerSocketChannel serverSocket;

    public EchoServerRefactor() throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress("localhost", 9999));
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        //将新连接处理器作为附件，绑定到sk选择键
        sk.attach(new AcceptorHandler());
    }

    @Override
    //轮询和分发事件
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

    private void dispatch(SelectionKey sk) {
        Runnable handler = (Runnable) (sk.attachment());
        //调用之前绑定到选择键的handler处理器对象
        if (handler != null) {
            //并没有启动新的线程
            handler.run();
        }
    }

    //新连接处理器
    private class AcceptorHandler implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel!=null){
                    new EchoHandler(selector,channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerRefactor()).start();
    }
}
