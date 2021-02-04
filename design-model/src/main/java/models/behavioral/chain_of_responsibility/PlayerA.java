package models.behavioral.chain_of_responsibility;

public class PlayerA extends Player {
    public PlayerA(Player successor) {
        this.setSuccessor(successor);
    }

    @Override
    public void handle(int i) {
        if (i==1){
            System.out.println("PlayerA 喝酒");
        }else {
            System.out.println("PlayerA把花向下传");
            next(i);
        }
    }
}
