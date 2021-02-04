package netty_redis_zookeeper.chapter3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * 使用SocketChannel在服务器端接收文件的实践案例
 * 本示例演示文件的接收，是服务器端的程序。
 * 和前面介绍的文件发送的SocketChannel客户端程序是相互配合使用的。
 * 由于在服务器端，需要用到选择器，所以在介绍完选择器后，
 * 才开始介绍NIO文件传输的Socket服务器端程序。
 * 服务器端接收文件的示例代码如下所示
 */
public class NioReceiveServer {
    private Charset charset=Charset.forName("UTF-8");
    /**
     * 内部类，服务器端保存的客户端对象，对应一个客户端文件
     */
    static class Client{
        //文件名称
        String fileName;
        //长度
        long fileLength;
        //开始传输的时间
        long startTime;
        //客户端的地址
        InetSocketAddress remoteAddress;
        //输出的文件通道
        FileChannel outChannel;
    }
    private ByteBuffer buffer=ByteBuffer.allocate(1024);
    //使用Map保存每个文件传输，当OP_READ可读时，根据通道找到对应的对象
    Map<SelectableChannel,Client> clientMap= new HashMap<SelectableChannel,Client>();
    public void startServer() throws IOException{
        //1.获取选择器
        Selector selector = Selector.open();
        //2.获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //3.设置非阻塞
        serverChannel.configureBlocking(false);
        //4.绑定连接
        serverChannel.bind(new InetSocketAddress("localhost",9999));
        //5.将通道注册到选择器上，并注册的IO事件为：接受新连接
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("serverChannel is listening...");
        //6.选择感兴趣的IO就绪事件
        while (selector.select()>0){
            //7.获取选择键集合
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                //8.获取单个选择键，并处理
                SelectionKey key = it.next();
                //9.判断key是具体的什么事件，是否为新连接事件
                if (key.isAcceptable()){
                    //10.若接受的时间是新连接，获取客户端新连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    if (socketChannel==null) continue;
                    //11.客户端新连接切换为非阻塞模式
                    socketChannel.configureBlocking(false);
                    //12.将客户端新连接注册到选择器上
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    //为每一条传输通道，建立一个Client客户端对象，放入map，供后面使用
                    Client client = new Client();
                    client.remoteAddress=(InetSocketAddress) socketChannel.getRemoteAddress();
                    clientMap.put(socketChannel,client);
                    System.out.println(socketChannel.getRemoteAddress()+"连接成功...");
                }else if (key.isReadable()){
                    //13.若接受的事件是 数据可读事件，读取客户端新连接
                    processData(key);
                }
                //NIO的特点只会累加，已选择的键的集合不会删除
                //如果不删除，下一次又会被select函数选中
                it.remove();
            }
        }
    }

    /**
     * 处理客户端传输过来的数据
     * @param key
     */
    private void processData(SelectionKey key) throws IOException {
        Client client = clientMap.get(key.channel());
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num=0;
        try{
            //读模式
            buffer.clear();
            while ((num=socketChannel.read(buffer))>0){
                buffer.flip();
                if (Objects.isNull(client.fileName)){
                    //客户端发送过来的首先是文件名
                    //根据文件名，创建服务器端的文件，将文件通道保存到客户端
                    String fileName = charset.decode(buffer).toString();
                    String fullName="C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\java\\src\\main\\java\\nio\\data\\"+fileName;
//                    String fullName="C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\java\\src\\main\\java\\nio\\data\\out.txt";
                    File file = new File(fullName);
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    client.fileName=fileName;
                    System.out.println("NIO 传输目标文件："+fullName);
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    client.outChannel=fileChannel;
                }else if (0==client.fileLength){
                    //客户端发送过来的，其次是文件长度
                    long fileLength = buffer.getLong();
                    client.fileLength=fileLength;
                    client.startTime=System.currentTimeMillis();
                    System.out.println("NIO 传输开始：");
                }else {
                    //客户端发送过来，最后是文件内容，写入文件内容
                    client.outChannel.write(buffer);
                }
                buffer.clear();
            }
            key.cancel();
        }catch (IOException e){
            key.cancel();
            e.printStackTrace();
            return;
        }
        //读取数量-1，表示客户端传输结束
        if (num==-1){
            client.outChannel.close();
            System.out.println("上传完毕");
            key.cancel();
            System.out.println("Size:"+client.fileLength);
            long endTime = System.currentTimeMillis();
            System.out.println("NIO IO传输毫秒数;"+(endTime-client.startTime));
        }
    }

    public static void main(String[] args) throws IOException {
        NioReceiveServer server = new NioReceiveServer();
        server.startServer();
    }
}
