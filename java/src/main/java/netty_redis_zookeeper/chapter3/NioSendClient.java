package netty_redis_zookeeper.chapter3;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 使用FileChannel文件通道读取本地文件内容，
 * 然后在客户端使用SocketChannel套接字通道，
 * 把文件信息和文件内容发送到服务器。
 * 客户端的完整代码如下：
 */
public class NioSendClient {
    private Charset charset=Charset.forName("UTF-8");

    /**
     * 向服务器端传输文件
     */
    public void sendFile() throws IOException {
        try {
            String srcPath = "C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\java\\src\\main\\java\\nio\\data\\input.txt";
            String desPath = "out.txt";
            File file = new File(srcPath);
            if (!file.exists()){
                System.out.println("文件不存在");
                return;
            }
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(
                    new InetSocketAddress("localhost", 9999)
            );
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()){
                //不断地自旋等待或者做其他的事
                System.out.println("等得脑壳痛");
            }
            System.out.println("Client成功连接服务器");
            //发送文件名称
            ByteBuffer fileNameByteBuffer = charset.encode(desPath);
            socketChannel.write(fileNameByteBuffer);
            //发送文件长度
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.putLong(file.length());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            //发送文件内容
            System.out.println("开始传输文件");
            int length=0;
            long progress=0;
            while ((length=fileChannel.read(buffer))>0){
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
                progress+=length;
                System.out.println("|"+(100*progress/file.length())+"%|");
            }
            if (length==-1){
                fileChannel.close();
                socketChannel.shutdownOutput();
                socketChannel.close();
            }
            System.out.println("============文件传输成功===========");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        NioSendClient client = new NioSendClient();
        client.sendFile();
    }
}
