package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//创建一个可重用固定个数的线程池，以共享的无界队列方式来运行这些线程。
public class NewFixedThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i=0;i<10;i++){
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try{
                        System.out.println(Thread.currentThread().getName()
                        +"正在被执行");
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
