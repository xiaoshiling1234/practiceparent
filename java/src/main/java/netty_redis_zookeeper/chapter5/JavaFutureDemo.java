package netty_redis_zookeeper.chapter5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;

public class JavaFutureDemo {
    public static final int SLEEP_GAP=500;
    public static String getCurThreadName(){
        return Thread.currentThread().getName();
    }
    static class HotWaterJob implements Callable<Boolean>{
        Logger logger = Logger.getLogger(this.getClass().getName());
        @Override
        public Boolean call() throws Exception {
            try {
                logger.info("洗好水壶");
                logger.info("灌上凉水");
                logger.info("放在火上");
                //线程睡眠一段时间代表烧水中
                Thread.sleep(SLEEP_GAP);
                logger.info("水开了");
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.info("发生异常被中断");
                return false;
            }
            logger.info("运行结束。");
            return true;
        }
    }
    static class WashThread implements Callable<Boolean>{
        Logger logger = Logger.getLogger(this.getClass().getName());
        @Override
        public Boolean call() throws Exception {
            try{
                logger.info("洗茶壶");
                logger.info("洗茶杯");
                logger.info("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.info("发生异常被中断");
                return false;
            }
            logger.info("运行结束");
            return true;
        }
    }

    public static void drinkTea(boolean waterOk,boolean cupOk){
        if (waterOk&&cupOk){
            System.out.println("泡茶喝");
        }else if (!waterOk){
            System.out.println("烧水失败，没有茶喝了");
        }else {
            System.out.println("烧水失败，没有茶喝了");
        }
    }

    public static void main(String[] args) {
        Callable<Boolean> hotWaterJob = new HotWaterJob();
        FutureTask<Boolean> hTask = new FutureTask<>(hotWaterJob);
        Thread hThread = new Thread(hTask, "**烧水-Thread");

        Callable<Boolean> wJob = new WashThread();
        FutureTask<Boolean> wTask = new FutureTask<>(wJob);
        Thread wThread = new Thread(hTask, "￥￥清洗-Thread");

        hThread.start();
        wThread.start();

        Thread.currentThread().setName("主线程");
        try {
            //阻塞
            Boolean waterOk = hTask.get();
            Boolean cupOk = wTask.get();
            drinkTea(waterOk,cupOk);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(getCurThreadName()+"发生异常被中断");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(getCurThreadName()+"运行结束");
    }
}
