package thread;

public class CreateByRunnable implements Runnable {
    private String name;

    public CreateByRunnable(String name) {
        this.name = name;
    }

    public void run() {
        for (int i =0;i<=10;i++){
            System.out.println(name+"运行,i="+i);
        }
    }

    public static void main(String[] args) {
        CreateByRunnable mt1 = new CreateByRunnable("线程A");
        CreateByRunnable mt2 = new CreateByRunnable("线程B");

        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt2);
        t1.start();
        t2.start();
    }
}
