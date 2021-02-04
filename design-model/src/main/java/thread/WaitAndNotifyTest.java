package thread;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Dongguabai
 * @date 2018/8/31 0:25
 */
@Slf4j
public class WaitAndNotifyTest {
    private static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new T1(), "线程一").start();
        new Thread(new T1(), "线程二").start();
        new Thread(new T1(), "线程三").start();
        new Thread(new T1(), "线程四").start();
        new Thread(new T1(), "线程五").start();
        Thread.sleep(2000);
        new Thread(new T2()).start();
        new Thread(new T2()).start();
        new Thread(new T2()).start();
        new Thread(new T2()).start();
        new Thread(new T2()).start();
    }

    static class T1 implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                try {
                    log.info(Thread.currentThread().getName() + "  线程【启动】！！！！，当前时间为：{}", LocalDateTime.now());
                    log.info(Thread.currentThread().getName() + "  正在wait---");
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info(Thread.currentThread().getName() + "  线程【结束】！！！！，当前时间为：{}", LocalDateTime.now());
        }
    }

    static class T2 implements Runnable {

        @Override
        public void run() {
            synchronized (obj) {
                log.info("T2线程【启动】！！！！，当前时间为：{}", LocalDateTime.now());
                log.info("T2准备notify---");
                obj.notify();
            }
            log.info("T2线程【结束】！！！！，当前时间为：{}", LocalDateTime.now());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
