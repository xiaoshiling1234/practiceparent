package thread;

public class CreateByExtendThread  extends Thread{
    private String name;

    public CreateByExtendThread(String name) {
        this.name = name;
    }

    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(name + "运行，i = " + i) ;
        }
    }

    public static void main(String[] args) {
        CreateByExtendThread mt1 = new CreateByExtendThread("线程A");
        CreateByExtendThread mt2 = new CreateByExtendThread("线程B");
        mt1.start();
        mt2.start();
    }
}

