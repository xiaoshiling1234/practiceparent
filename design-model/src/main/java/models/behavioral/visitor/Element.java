package models.behavioral.visitor;


//抽象元素角色
public abstract class Element {
    //接受操作
    public abstract void accept(Visitor vi);
}
