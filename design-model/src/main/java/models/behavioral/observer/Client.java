package models.behavioral.observer;

public class Client {
    public static void main(String[] args) {
        ConcreateSubject subject = new ConcreateSubject();
        ConcreateObserver observer = new ConcreateObserver();
        subject.attach(observer);
        subject.change();
    }
}
