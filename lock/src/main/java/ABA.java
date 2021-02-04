import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

// 下面的代码分别用AtomicInteger和AtomicStampedReference来对初始值为100的原子整型变量进行更新，
// AtomicInteger会成功执行CAS操作，而加上版本戳的AtomicStampedReference对于ABA问题会执行CAS失败：
public class ABA {
    private static AtomicInteger atomicInteger=new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedReference=
            new AtomicStampedReference<Integer>(100,0);

    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            public void run() {
                atomicInteger.compareAndSet(100, 101);
                atomicInteger.compareAndSet(101, 100);
            }
        });
        Thread intT2 = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean c3 = atomicInteger.compareAndSet(100, 101);
                System.out.println(c3);//true
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedReference.compareAndSet(100, 101,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                atomicStampedReference.compareAndSet(101, 100,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            }
        });
        Thread refT2 = new Thread(new Runnable() {
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                System.out.println("before sleep : stamp = " + stamp);    // stamp = 0
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after sleep : stamp = " + atomicStampedReference.getStamp());//stamp = 1
                boolean c3 = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(c3);//false
            }
        });
        refT1.start();
        refT2.start();

    }
}
