package netty_redis_zookeeper.chapter3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIOCopyDemo {
    public static void main(String[] args) {
        String sourcePath = "C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\java\\src\\main\\java\\nio\\data\\input.txt";
        String desPath = "C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\java\\src\\main\\java\\nio\\data\\out.txt";
        //演示复制资源文件
        nioCopyResourceFile(sourcePath, desPath);
    }

    private static void nioCopyResourceFile(String sourcePath, String desPath) {
        File srcFile = new File(sourcePath);
        File destFile = new File(desPath);
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            long startTime = System.currentTimeMillis();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();
                int length = -1;
                ByteBuffer buf = ByteBuffer.allocate(1024);
                //读
                while ((length = inChannel.read(buf)) != -1) {
                    //翻转变成读取模式
                    buf.flip();
                    int outLength = 0;
                    while ((outLength = outChannel.write(buf)) != 0) {
                        System.out.println("写入字节数：" + outLength);
                    }
                    //第二次切换：变成写模式
                    buf.clear();
                }
                //强制刷盘
                outChannel.force(true);
            } finally {
                outChannel.close();
                fos.close();
                inChannel.close();
                fis.close();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("复制毫秒数：" + (endTime - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
