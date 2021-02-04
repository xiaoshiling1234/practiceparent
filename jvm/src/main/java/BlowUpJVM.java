import com.sun.org.apache.xml.internal.serialize.Printer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static jdk.nashorn.internal.objects.Global.println;

public class BlowUpJVM {

    //    2、栈深度溢出
    public static void testStackOverFlow() {
        BlowUpJVM.testStackOverFlow();
    }

    //    栈不断递归，而且没有处理，所以虚拟机栈就不断深入不断深入，栈深度就这样溢出了。
//            3、永久代内存溢出
    public static void testPergemOutOfMemory1() {
        //方法一失败
        List<String> list = new ArrayList<String>();

        while (true) {
            list.add(UUID.randomUUID().toString().intern());
        }
    }

//    //    打算把String常量池堆满，没想到失败了，JDK1.7后常量池放到了堆里，也能进行垃圾回收了。然后换种方式，使用cglib，用Class把老年代取堆满
//    public static void testPergemOutOfMemory2() {
//        try {
//            while (true) {
//                Enhancer enhancer = new Enhancer();
//                enhancer.setSuperclass(OOM.class);
//                enhancer.setUseCache(false);
//                enhancer.setCallback(new MethodInterceptor() {
//                    @Override
//                    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//                        return proxy.invokeSuper(obj, args);
//                    }
//                });
//                enhancer.create();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    虚拟机成功内存溢出了，那JDK动态代理产生的类能不能溢出呢？
//    public static void testPergemOutOfMemory3() {
//        while (true) {
//            final OOM oom = new OOM();
//            Proxy.newProxyInstance(oom.getClass().getClassLoader(), oom.getClass().getInterfaces(), new InvocationHandler() {
//                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                    Object result = method.invoke(oom, args);
//                    return result;
//                }
//            });
//        }
//    }

    //    事实表明，JDK动态代理差生的类不会造成内存溢出，原因是：JDK动态代理产生的类信息，不会放到永久代中，而是放在堆中。
//            4、本地方法栈溢出
    public static void testNativeMethodOutOfMemory() {
        int j = 0;
        while (true) {
            println(j++);
            ExecutorService executors = Executors.newFixedThreadPool(50);
            int i = 0;
            while (i++ < 10) {
                executors.submit(new Runnable() {
                    public void run() {
                    }
                });
            }
        }
    }

    //    这个的原理就是不断创建线程池，而每个线程池都创建10个线程，这些线程池都是在本地方法区的，久而久之，本地方法区就溢出了。
//            5、JVM栈内存溢出
    public static void testStackOutOfMemory() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                    }
                }
            });
            thread.start();
        }
    }

    //    线程的创建会直接在JVM栈中创建，但是本例子中，没看到内存溢出，主机先挂了，不是JVM挂了，真的是主机挂了，无论在mac还是在windows，都挂了。
//    温馨提示，这个真的会死机的。
//            6、堆溢出
    public static void testOutOfHeapMemory() {
        List<StringBuffer> list = new ArrayList<StringBuffer>();
        while (true) {
            StringBuffer B = new StringBuffer();
            for (int i = 0; i < 10000; i++) {
                B.append(i);
            }
            list.add(B);
        }
    }
//    不断往堆中塞新增的StringBuffer对象，堆满了就直接溢出了。
}
