package jvm;

/**
 * @author Zeng
 * @date 2020/4/6 11:41
 */
public class ReferenceCountingGC {

    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    /**
     * 占点内存，以便观察清楚GC日志中是否有进行垃圾收集
     */
    private byte[] bigSize = new byte[2 * _1MB];
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
    }
    public static void testGC() throws InterruptedException {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        //对象内部的instance引用指针相互指向对方实例
        objA.instance = objB;
        objB.instance = objA;
        //除了instance指针以外没有其它引用指针指向这两个对象
        objA = null;
        objB = null;
        System.gc();
        //执行finalize()方法的速度有些慢，让主线程等待一下它执行
        Thread.sleep(500);
    }
    public static void main(String[] args) throws InterruptedException {
        testGC();
    }
}
