package models.structural.flyweight;

//享元模式是以共享的方式高效地支持大量的细粒度对象。
//享元对象能做到共享的关键是区分内部状态（Internal State）
//和外部状态（ExternalState）。

//抽象享元
public interface Flyweight {
    public abstract void operation(String externalState);
}
