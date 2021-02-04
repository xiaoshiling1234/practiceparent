package 算法;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class 不用第三方变量如何交换两个数的值 {
    public static void main(String[] args) {
        //0000 0011 | 0000 0101 = 0000 0110
        int a=3,b=5;
        a=a^b;
        System.out.println(a);
        b=a^b;//a
        System.out.println(b);
        a=a^b;//b
        System.out.println(a);
    }
}
