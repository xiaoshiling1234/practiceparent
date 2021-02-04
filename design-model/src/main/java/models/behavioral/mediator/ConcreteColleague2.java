package models.behavioral.mediator;

public class ConcreteColleague2 extends Colleague {
    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void action() {
        System.out.println("这是同事2的行动方法！");
    }
}
