package models.behavioral.iterator;

//意思是：提供一种方法访问一个容器对象中各个元素，而又不需暴露该对象的内部细节。
public interface Iterator {
    public Object next();
    public boolean hasNext();
}
