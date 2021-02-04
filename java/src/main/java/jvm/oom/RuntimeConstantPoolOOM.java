package jvm.oom;

import java.util.ArrayList;

/**
 * 运行时常量池导致的内存溢出异常
 * 限制方法区大小
 * -XX:PermSize=1M -XX:MaxPermSize=1M
 * PermGen space
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        //使用List保持着常量池引用，避免Full GC回收常量池回收
        ArrayList<String> list = new ArrayList<>();
        int i = 10;
        while (true) {
            //String.intern()是一个Native方法，它的作用是：如果字符串常量池中已经
            //包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象；
            //否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
            list.add(String.valueOf(i++).intern());
        }
    }
}
