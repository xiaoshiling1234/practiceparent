package models.behavioral.observer;

public interface Subject {
    //登记
    public void attach(Observer obs);
    //删除
    public void detach(Observer obs);
    //通知
    public void notifyObserver();
}
