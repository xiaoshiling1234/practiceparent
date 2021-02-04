package models.behavioral.state;

//环境角色
public class Context {
    public static State STATE1 = new ConcreateState1();
    public static State STATE2 = new ConcreateState2();
    private State currentState;

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        currentState.setContext(this);
    }

    //行为委托
    public void handle1() {
        this.setCurrentState(STATE1);
        this.currentState.handle();
    }

    public void handle2() {
        this.setCurrentState(STATE2);
        this.currentState.handle();
    }
}
