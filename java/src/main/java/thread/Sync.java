package thread;

public class Sync implements Runnable {
    private int ticket=5;
    public void run() {
        for (int i=0;i<100;i++){
            synchronized (this){
                if (ticket>0){
                    try {
                        Thread.sleep(300);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("卖票：ticket="+ticket--);
                }
            }
        }
    }

    public static void main(String[] args) {
        Sync mt1 = new Sync();
        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt1);
        Thread t3 = new Thread(mt1);
        t1.start();
        t2.start();
        t3.start();
    }
}
