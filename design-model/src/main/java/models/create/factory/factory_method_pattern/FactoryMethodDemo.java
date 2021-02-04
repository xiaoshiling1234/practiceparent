package models.create.factory.factory_method_pattern;

public class FactoryMethodDemo {
    public static void main(String[] args) {
        ConcreateCreator concreateCreator = new ConcreateCreator();
        ConcreateProduct product = concreateCreator.factory(ConcreateProduct.class);
        product.method1();
        product.method2();
    }
}
