package models.structural.bridge;

public class Client {
    public static void main(String[] args) {
        Implementor imp = new ConcreteImplementor();
        Abstraction abs = new RefinedAbstraction(imp);
        abs.operation();
    }
}
