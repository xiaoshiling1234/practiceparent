package models.behavioral.command;

public class ConcreateCommand implements Command {
    private Receiver receiver;

    public ConcreateCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.action();
    }
}
