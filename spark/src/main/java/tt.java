import scala.collection.mutable.HashTable;

import java.util.HashMap;
import java.util.Hashtable;


public class tt {


    // 采用 Runnable 接口方式创建的多条线程可以共享实例属性

    private int i;


    // 同步增加方法

    private synchronized void inc() {

        i++;

        System.out.println(Thread.currentThread().getName() + "--inc--" + i);

    }


    // 同步减算方法

    private synchronized void dec() {

        i--;

        System.out.println(Thread.currentThread().getName() + "--dec--" + i);

    }


// 增加线程

    class Inc implements Runnable {

        public void run() {

            inc();

        }

    }


    // 减算线程

    class Dec implements Runnable {

        public void run() {

            dec();

        }

    }

    public static void main(String[] args) {
        tt tt = new tt();
        Inc inc = tt.new Inc();
        Dec dec = tt.new Dec();
        for (int i=0;i<2;i++){
            new Thread(inc).start();
            new Thread(dec).start();
        }
    }

}
