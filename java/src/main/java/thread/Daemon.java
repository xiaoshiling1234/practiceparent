package thread;

public class Daemon implements Runnable {
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getName()
            +"在运行。");
        }
    }

    public static void main(String[] args) {
        Daemon mt1 = new Daemon();
        Thread t = new Thread(mt1, "子线程");
        t.setDaemon(true);
        t.start();
    }
}
