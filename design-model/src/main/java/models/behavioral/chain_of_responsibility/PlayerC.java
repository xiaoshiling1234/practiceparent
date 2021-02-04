package models.behavioral.chain_of_responsibility;

public class PlayerC extends Player {
    public PlayerC(Player successor) {
        this.setSuccessor(successor);
    }

    @Override
    public void handle(int i) {
        if (i==3){
            System.out.println("PlayerC 喝酒");
        }else {
            System.out.println("PlayerC把花向下传");
            next(i);
        }
    }
}
