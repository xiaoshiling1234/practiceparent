package models.behavioral.templatemethod;

public class Client {
    public static void main(String[] args) {
        AbstractClass ac=new ConcreteTemplate();
        ac.templateMethod();
    }
}
