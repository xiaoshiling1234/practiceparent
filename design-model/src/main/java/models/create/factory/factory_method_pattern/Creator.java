package models.create.factory.factory_method_pattern;

//抽象工厂
public interface Creator {
    public <T extends Product> T factory(Class<T> c);
}
