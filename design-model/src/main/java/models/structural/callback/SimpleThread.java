package models.structural.callback;

public class SimpleThread extends ThreadHolder{
    @Override
    public void run() {
        System.out.println("start!");
    }
}
