package models.behavioral.mediator;

public class ConcreateMediator extends Mediator {
    private ConcreteColleague1 c1;
    private ConcreteColleague2 c2;
    @Override
    public void colleaggueChanged(Colleague c) {
        c1.action();
        c2.action();
    }
    //工厂方法，创建同事对象

    public void createMediator(){
        c1=new ConcreteColleague1(this);
        c2=new ConcreteColleague2(this);
    }

    public ConcreteColleague1 getC1() {
        return c1;
    }

    public ConcreteColleague2 getC2() {
        return c2;
    }
}
