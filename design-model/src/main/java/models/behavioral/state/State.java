package models.behavioral.state;

//抽象状态
public abstract class State {
    //定义一个环境角色
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    public abstract void handle();
}
