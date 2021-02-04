package thread;

/**
 * 方法将一个线程的操作暂时让给其他线程执行
 */
public class Yield implements Runnable{
    public void run() {
        for (int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            }catch (Exception e){}
            System.out.println(Thread.currentThread().getName()
                    + "运行，i = " + i) ;  // 取得当前线程的名字
            if (i==2){
                System.out.println("线程礼让:");
                Thread.currentThread().yield();
            }
        }
    }

    public static void main(String[] args) {
        Yield mt1 = new Yield();
        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt1);
        t1.start();
        t2.start();
    }
}
