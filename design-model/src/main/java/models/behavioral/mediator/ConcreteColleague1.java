package models.behavioral.mediator;

public class ConcreteColleague1 extends Colleague {
    public ConcreteColleague1(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void action() {
        System.out.println("这是同事1的行动方法！");
    }
}
