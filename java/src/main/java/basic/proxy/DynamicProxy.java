package basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 动态生成的代理类本身的一些特点
 * 1. 包：如果所代理的接口都是 public 的，那么它将被定义在顶层包（即包路径为空），如果所代理的接口中有非 public 的接口（因为接口不能被定义为 protect或private，所以除 public之外就是默认的package访问级别，那么它将被定义在该接口所在包，这样设计的目的是为了最大程度的保证动态代理类不会因为包管理的问题而无法被成功定义并访问；
 * 2. 类修饰符：该代理类具有 final 和 public 修饰符，意味着它可以被所有的类访问，但是不能被再度继承；
 * 3. 类名：格式是“$ProxyN”，其中 N 是一个逐一递增的阿拉伯数字，代表 Proxy 类第 N 次生成的动态代理类，值得注意的一点是，并不是每次调用 Proxy 的静态方法创建动态代理类都会使得 N 值增加，原因是如果对同一组接口（包括接口排列的顺序相同）试图重复创建动态代理类，它会很聪明地返回先前已经创建好的代理类的类对象，而不会再尝试去创建一个全新的代理类，这样可以节省不必要的代码重复生成，提高了代理类的创建效率。
 * 4. 类继承关系：Proxy 类是它的父类，这个规则适用于所有由 Proxy 创建的动态代理类。而且该类还实现了其所代理的一组接口;
 * 美中不足
 * Proxy只能对interface进行代理，无法实现对class的动态代理。观察动态生成的代理继承关系图可知原因，他们已经有一个固定的父类叫做Proxy，Java语法限定其不能再继承其他的父类
 */
public class DynamicProxy {
    interface IHello{
        void sayHello();
    }
    static class Hello implements IHello{

        @Override
        public void sayHello() {
            System.out.printf("hello world");
        }
    }
    static class DynamicProxyTest implements InvocationHandler{
        Object originalObj;
        Object bind(Object originalObj){
            this.originalObj=originalObj;
            return Proxy.newProxyInstance(
                    originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(),this
            );
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.printf("welcome");
            return method.invoke(originalObj,args);
        }
    }

    public static void main(String[] args) {
        //设置这个值，在程序运行完成后，可以生成代理类
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IHello hello = (IHello) new DynamicProxyTest().bind(new Hello());
        hello.sayHello();
    }
}
