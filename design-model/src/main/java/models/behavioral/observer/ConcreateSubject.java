package models.behavioral.observer;

import java.util.Enumeration;
import java.util.Vector;

public class ConcreateSubject implements Subject {
    private Vector<Observer> observers = new Vector<Observer>();

    @Override
    public void attach(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void detach(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObserver() {
        for (Observer e : observers) {
            e.update();
        }
    }

    public Enumeration<Observer> observers(){
        return observers.elements();
    }
    public void change(){
        this.notifyObserver();
    }
}
