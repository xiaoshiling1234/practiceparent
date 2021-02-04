package netty_redis_zookeeper.chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class UDPClient {
    public void send() throws IOException{
        //获取DatagramChannel数据报通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        //设置非阻塞
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        System.out.println("UDP客户端启动成功！");
        System.out.println("请输入发送内容");
        while (scanner.hasNext()){
            String next = scanner.next();
            buffer.put((System.currentTimeMillis()+">>"+next).getBytes());
            buffer.flip();
            //通过DatagramChannel数据报通道发送数据
            datagramChannel.send(buffer,
                    new InetSocketAddress("localhost",9999));
            buffer.clear();
        }
        //关闭通道
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new UDPClient().send();
    }
}
