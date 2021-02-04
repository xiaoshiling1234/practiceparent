package thread;

public class ThreadName implements Runnable {
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"运行");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new ThreadName());
        t1.start();
        Thread t2 = new Thread(new ThreadName(),"t2");
        t2.start();
    }
}
