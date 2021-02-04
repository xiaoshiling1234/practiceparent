package jvm.gc;

/**
 * -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC
 * 虚拟机提供了一个-XX:PretenureSizeThreshold参数，令大于这个设置值的对象直接在老年代分配
 *
 * PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效，
 * ParallelScavenge收集器不认识这个参数，Parallel Scavenge收集器
 * 一般并不需要设置。如果遇到必须使用此参数的场合，可以考虑
 * ParNew加CMS的收集器组合。
 */
public class TestPretenureSizeThreshold {
    private static final int _1MB=1024*1024;
    public static void main(String[] args) {
        byte[] allocation;
        allocation=new byte[4*_1MB];
    }
}
