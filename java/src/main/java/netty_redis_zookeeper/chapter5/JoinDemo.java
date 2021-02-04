package netty_redis_zookeeper.chapter5;

import java.util.logging.Logger;

public class JoinDemo {
    public static final int SLEEP_GAP = 500;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterThread extends Thread {
        Logger logger = Logger.getLogger(this.getName());
        public HotWaterThread() {
            super("**烧水-Thread");
        }

        public void run() {
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
            }
            logger.info("运行结束。");
        }
    }
    static class WashThread extends Thread{
        Logger logger = Logger.getLogger(this.getName());
        public WashThread(){
            super("$$清洗-Thread");
        }

        @Override
        public void run() {

            try{
                logger.info("洗茶壶");
                logger.info("洗茶杯");
                logger.info("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.info("发生异常被中断");
            }
            logger.info("运行结束");
        }
    }

    public static void main(String[] args) {
        HotWaterThread hotWaterThread = new HotWaterThread();
        WashThread washThread = new WashThread();
        hotWaterThread.start();
        washThread.start();
        try {
            //合并烧水-线程
            hotWaterThread.join();
            //合并清洗-线程
            washThread.join();
            Thread.currentThread().setName("主线程");
            System.out.println("泡茶喝");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(getCurThreadName()+"发生异常被中断");
        }
        System.out.println(getCurThreadName()+"运行结束");
    }
}
