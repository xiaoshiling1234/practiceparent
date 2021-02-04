package thread;

public class Sleep implements Runnable {

    public void run() {
        for (int i=0;i<50;i++){
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){

            }
            System.out.println(Thread.currentThread().getName()
                    + "运行，i = " + i) ;  // 取得当前线程的名字
        }
    }

    public static void main(String[] args) {
        Sleep mt1 = new Sleep();
        Thread t = new Thread(mt1, "线程");
        t.start();
    }
}
