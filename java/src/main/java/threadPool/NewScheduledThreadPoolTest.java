package threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//创建一个定长线程池，支持定时及周期性任务执行
public class NewScheduledThreadPoolTest {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        System.out.println("延迟一秒后，每三秒执行一次");
                    }
                },1,3, TimeUnit.SECONDS
        );
    }
}
