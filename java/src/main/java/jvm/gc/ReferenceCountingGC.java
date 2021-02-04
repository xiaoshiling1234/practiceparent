package jvm.gc;

/**
 * 引用计数算法的缺陷 无法解决循环引用问题
 * -XX:+PrintGC 输出GC日志
 * -XX:+PrintGCDetails 输出GC的详细日志
 * -XX:+PrintGCTimeStamps 输出GC的时间戳（以基准时间的形式）
 * -XX:+PrintGCDateStamps 输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800）
 * -XX:+PrintHeapAtGC 在进行GC的前后打印出堆的信息
 * -Xloggc:../logs/gc.log 日志文件的输出路径
 */
public class ReferenceCountingGC {
    public Object instance=null;
    private static final int _1MB=1024*1024;
    /**
     * 创建一个占点内存的
     */
    private byte[] bigSize= new byte[2*_1MB];
    public static void main(String[] args){
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance=objB;
        objB.instance=objA;
        objA=null;
        objB=null;
        //gc
        System.gc();
    }
//[GC (System.gc())  9311K->816K(249344K), 0.0008879 secs]
//[Full GC (System.gc())  816K->743K(249344K), 0.0040966 secs]
//从运行结果中可以清楚看到，GC日志中包含“9311K->816K”，
//意味着虚拟机并没有因为这两个对象互相引用就不回收它们，这也从侧面说明虚拟
//机并不是通过引用计数算法来判断对象是否存活的。

}

