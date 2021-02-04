package thread;


/**
 * 在线程操作中，可以使用 join() 方法让一个线程强制运行，线程强制运行期间，
 * 其他线程无法运行，必须等待此线程完成之后才可以继续执行。
 */
class MyThread implements Runnable {
    public void run() {
        for (int i = 0; i < 500; i++) {
            System.out.println(Thread.currentThread().getName() +
                    "运行，i=" + i);
        }
}
}

public class Join {
    public static void main(String[] args) {
        MyThread mt1 = new MyThread();
        Thread t = new Thread(mt1, "线程");
        t.start();
        for (int i = 0; i < 50; i++) {
            if (i > 10) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Main线程运行 --> " + i);
        }

    }
}
