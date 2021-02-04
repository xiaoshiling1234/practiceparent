package models.behavioral.observer;

public class ConcreateObserver implements Observer {
    @Override
    public void update() {
        System.out.println("收到通知，并进行处理！");
    }
}
