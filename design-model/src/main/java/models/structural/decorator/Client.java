package models.structural.decorator;

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreateComponent();
        ConcreteDecorator concreteDecorator = new ConcreteDecorator(component);
        concreteDecorator.operation();
    }
}
