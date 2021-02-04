package jvm;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author Zeng
 * @date 2020/4/7 8:16
 * 四种引用类型实践
 */
public class Reference {

    private String name;
    public Reference(String name) {
        this.name = name;
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(name + " finalize method executed!");
    }
    public static void main(String[] args) throws InterruptedException {
        //强引用
        Reference obj = new Reference("强引用obj");
        System.gc();
        Thread.sleep(500);
        //软引用
        Reference objA = new Reference("软引用objA");
        SoftReference<Reference> softReferenceA = new SoftReference<>(objA);
        objA = null;
        System.gc();
        Thread.sleep(500);
        //弱引用
        Reference objB = new Reference("弱引用objB");
        WeakReference<Reference> weakReferenceB = new WeakReference<>(objB);
        objB = null;
        System.gc();
        Thread.sleep(500);
        //虚引用
        Reference objC = new Reference("虚引用objC");
        ReferenceQueue<Reference> phantomQueue = new ReferenceQueue<>();
        PhantomReference<Reference> phantomReference = new PhantomReference<>(objC, phantomQueue);
        objC = null;
        System.gc();
        Thread.sleep(500);
    }
}

