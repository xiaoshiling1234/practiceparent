package netty_redis_zookeeper.chapter5;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class GuavaFutureDemo {
    public static final int SLEEP_GAP = 500;

    public static String getCurThreadName() {
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
    static class WashJob implements Callable<Boolean>{
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

    //新建一个一部业务类型作为泡茶喝主线程
    static class MainJob implements Runnable{
        boolean waterOk=false;
        boolean cupOk=false;
        int gap=SLEEP_GAP/10;
        @Override
        public void run() {
            while (true){
                try{
                    Thread.sleep(gap);
                    System.out.println("读书中....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(getCurThreadName()+"发生异常被中断");
                }
                if (waterOk&&cupOk){
                    drinkTea(waterOk,cupOk);
                }
            }
        }

        public void drinkTea(boolean waterOk,boolean cupOk){
            if (waterOk&&cupOk){
                System.out.println("泡茶喝");
                this.waterOk=false;
                this.gap= SLEEP_GAP*100;
            }else if (!waterOk){
                System.out.println("烧水失败，没有茶喝了");
            }else {
                System.out.println("烧水失败，没有茶喝了");
            }
        }
    }

    public static void main(String[] args) {
        //创建一个新的线程实力，作为泡茶主线程
        MainJob mainJob = new MainJob();
        Thread mainThread = new Thread(mainJob);
        mainThread.setName("主线程");
        mainThread.start();
        //烧水
        HotWaterJob hotWaterJob = new HotWaterJob();
        //清洗
        WashJob washJob = new WashJob();

        //创建java线程池
        ExecutorService jPool = Executors.newFixedThreadPool(10);

        //包装Java线程池，构造guava线程池
        ListeningExecutorService gPool = MoreExecutors.listeningDecorator(jPool);

        //提交烧水的业务逻辑实例，到Guava线程池获取异步任务
        ListenableFuture<Boolean> hotFuture = gPool.submit(hotWaterJob);
        //绑定异步回调，烧水完成后，把喝水任务的waterOk标志设置为true
        Futures.addCallback(hotFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean){
                    mainJob.waterOk=true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("烧水失败，没有茶喝了");
            }
        });

        //提交清洗的业务逻辑实例，到Guava线程池获取异步任务
        ListenableFuture<Boolean> washFuture = gPool.submit(washJob);
        //绑定异步回调，烧水完成后，把喝水任务的waterOk标志设置为true
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean){
                    mainJob.cupOk=true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("杯子洗不了，没有茶喝了");
            }
        });

    }
}
