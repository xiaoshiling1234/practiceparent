package models.behavioral.command;

public class Client {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        Command command = new ConcreateCommand(receiver);
        invoker.setCommand(command);
        invoker.action();
    }
}
