package models.create.factory.abstract_factory_pattern;

public class ClientDemo {
    public static void main(String[] args) {
        AbstractFactory factory1 = new ConcreteFactory1();
        AbstractFactory factory2 = new ConcreteFactory2();
        factory1.factoryA().method1();
        factory1.factoryB().method1();
        factory2.factoryA().method1();
        factory2.factoryB().method1();
    }
}
