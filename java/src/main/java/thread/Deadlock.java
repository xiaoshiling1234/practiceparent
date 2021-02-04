package thread;



class ZhangSan{
    public void say(){
        System.out.println("张三对李四说：“你给我画，我就把书给你。");
    }
    public void get(){
        System.out.println("张三得到画了");
    }
}
class LiSi{
    public void say(){
        System.out.println("李四对张三说：“你给我书，我就把画给你");
    }
    public void get(){
        System.out.println("李四得到书了。");
    }
}

public class Deadlock  implements Runnable {
    private static ZhangSan zhangSan=new ZhangSan();
    private static LiSi liSi=new LiSi();
    private boolean flag = false ;  // 声明标志位，判断那个先说话
    public void run() {
        if (flag){
            synchronized (zhangSan){
                zhangSan.say();
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (liSi){
                    zhangSan.get();
                }
            }
        }else {
            synchronized (liSi){
                liSi.say();
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (zhangSan){
                    liSi.get();
                }
            }
        }
    }

    public static void main(String[] args) {
        Deadlock t1 = new Deadlock();
        Deadlock t2 = new Deadlock();
        t1.flag=true;
        t2.flag=false;
        Thread thA = new Thread(t1);
        Thread thB = new Thread(t1);
        thA.start();
        thB.start();
    }
}