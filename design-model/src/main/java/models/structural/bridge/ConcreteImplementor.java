package models.structural.bridge;
//具体实现化角色
public class ConcreteImplementor implements Implementor{
    @Override
    public void operationImp() {
        System.out.println("ConcreteImplementor");
    }
}
