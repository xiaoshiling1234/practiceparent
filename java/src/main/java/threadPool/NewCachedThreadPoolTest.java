package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可缓存线程池，先查看池中有没有以前建立的线程，如果有，就直接使用。
 * 如果没有，就建一个新的线程加入池中，
 * 缓存型池子通常用于执行一些生存期很短的异步型任务
 */
public class NewCachedThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cacheThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName()
                    +"正在被执行");
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
