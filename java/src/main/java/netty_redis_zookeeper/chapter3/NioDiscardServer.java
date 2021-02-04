package netty_redis_zookeeper.chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Discard服务器的功能很简单：
 * 仅仅读取客户端通道的输入数据，读取完成后直接关闭客户端通道；
 * 并且读取到的数据直接抛弃掉（Discard）。
 * Discard服务器足够简单明了，作为第一个学习NIO的通信实例，较有参考价值。
 */
public class NioDiscardServer {
    public static void startServer() throws IOException{
        //1.获取选择器
        Selector selector = Selector.open();
        //2.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3.设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //4.绑定连接
        serverSocketChannel.bind(new InetSocketAddress("localhost",9999));
        System.out.println("服务器启动成功");
        //5.将通道注册的”接受新连接“注册到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6.轮询感兴趣的IO就绪事件
        while (selector.select()>0){
            //7.获取选择键集合
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()){
                //8.获取单个的选择键并处理
                SelectionKey selectionKey = selectionKeyIterator.next();
                //9.判断key是具体的什么事件
                if (selectionKey.isAcceptable()){
                    //10.若选择键是连接就绪，就获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //11.设置非阻塞模式
                    socketChannel.configureBlocking(false);
                    //12.将该新连接的通道的可读事件，注册到选择器上
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if (selectionKey.isReadable()){
                    //13.若选择键的IO时间是可读，读取数据
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    //14.读取数据，然后丢弃
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length=0;

                    while ((length=socketChannel.read(byteBuffer))>0){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,length));
                        byteBuffer.clear();
                    }
                    //如果获取之后就关闭，则只能发送一次消息
                    socketChannel.close();
                }
                //15.移除选择键
                selectionKeyIterator.remove();
            }
        }
        //16.关闭连接
        serverSocketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}
