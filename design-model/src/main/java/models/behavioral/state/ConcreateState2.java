package models.behavioral.state;

public class ConcreateState2 extends State {
    @Override
    public void handle() {
        System.out.println("行为2的逻辑处理");
    }
}
