package models.behavioral.state;

public class ConcreateState1 extends State {
    @Override
    public void handle() {
        System.out.println("行为1的逻辑处理");
    }
}
