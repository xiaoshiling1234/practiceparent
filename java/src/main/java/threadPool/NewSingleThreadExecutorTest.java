package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//它只会用唯一的工作线程来执行任务，保证所有
// 任务按照指定顺序(FIFO, LIFO, 优先级)执行。
public class NewSingleThreadExecutorTest {
    public static void main(String[] args) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i=0;i<10;i++){
            final int index=i;
            singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"正在被执行,打印的值是:"+index);
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
