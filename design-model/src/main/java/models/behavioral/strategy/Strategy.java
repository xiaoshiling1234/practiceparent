package models.behavioral.strategy;

//其用意是针对一组算法，将每一个算法封装到具有共同接口的独立的类中，
// 从而使得它们可以相互替换，使得算法可以在不影响到客户端的情况下发生变化。
public abstract class Strategy {
    public abstract void strategyInterface();
}
