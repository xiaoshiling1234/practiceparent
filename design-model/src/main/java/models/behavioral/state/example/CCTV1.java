package models.behavioral.state.example;

public class CCTV1 implements Channel {
    @Override
    public void display() {
        System.out.println("CCTV1新闻联播");
    }
}
