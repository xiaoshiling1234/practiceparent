package thread;

/**
 * 当一个线程运行时，另外一个线程可以
 * 直接通过interrupt()方法中断其运行状态。
 */
public class Interrupt implements Runnable {
    public void run() {
        System.out.println("1.进入run");
        try {
            Thread.sleep(10000);
            System.out.println("2.完成休眠");
        }catch (InterruptedException e){
            System.out.println("3.休眠被终止");
            return;// 返回调用处
        }
        System.out.println("4.run结束");
    }

    public static void main(String[] args) {
        Interrupt mt1 = new Interrupt();
        Thread t = new Thread(mt1, "线程");
        t.start();
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println("main 休眠中止");
        }
        t.interrupt();//中断线程执行
    }
}
