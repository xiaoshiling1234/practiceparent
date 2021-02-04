package models.behavioral.templatemethod;

//抽象模板
public abstract class AbstractClass
{
    protected abstract void operation();
    //模板方法
    public void templateMethod(){
        this.operation();
    }
}
