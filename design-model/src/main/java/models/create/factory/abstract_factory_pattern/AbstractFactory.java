package models.create.factory.abstract_factory_pattern;

//创建产品族
public interface AbstractFactory {
    public ProductA factoryA();
    public ProductB factoryB();
}
