package models.create.factory.factory_method_pattern;

public class ConcreateProduct implements Product {
    @Override
    public void method1() {
        System.out.println("method1");
    }

    @Override
    public void method2() {
        System.out.println("method2");
    }
}
