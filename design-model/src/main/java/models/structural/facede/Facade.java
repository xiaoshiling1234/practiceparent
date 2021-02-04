package models.structural.facede;
//外观模式
//意思是：要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行。外观模式提供一个高层次的接口，使得子系统更易使用。
public class Facade {
    private ClassA a=new ClassA();
    private ClassB b=new ClassB();
    private ClassC c=new ClassC();

    public void methodA(){
        a.methodA();
    }
    public void methodB(){
        b.methodB();
    }
    public void methodC(){
        c.methodC();
    }
}
