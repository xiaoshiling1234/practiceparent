package models.behavioral.chain_of_responsibility;

public class PlayerB extends Player {
    public PlayerB(Player successor) {
        this.setSuccessor(successor);
    }

    @Override
    public void handle(int i) {
        if (i==2){
            System.out.println("PlayerB 喝酒");
        }else {
            System.out.println("PlayerB把花向下传");
            next(i);
        }
    }
}
