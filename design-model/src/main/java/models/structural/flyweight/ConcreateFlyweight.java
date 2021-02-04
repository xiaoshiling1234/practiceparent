package models.structural.flyweight;

public class ConcreateFlyweight implements Flyweight {
    private String intrinsicState;//内部状态

    ConcreateFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }

    @Override
    public void operation(String externalState) {
        System.out.println("内部状态：" + intrinsicState +
                ",外部状态" + externalState);
    }
}
