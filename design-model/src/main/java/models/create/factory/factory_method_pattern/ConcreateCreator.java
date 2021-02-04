package models.create.factory.factory_method_pattern;

//具体工厂ConcreteCreator
public class ConcreateCreator implements Creator {
    @Override
    public <T extends Product> T factory(Class<T> c) {
        Product product = null;
        try {
            product = (Product) Class.forName(c.getName())
                    .newInstance();
        } catch (Exception e) {
        }
        return (T) product;
    }
}
