package models.behavioral.command;
//调用者
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }
    public void action(){
        this.command.execute();
    }
}
