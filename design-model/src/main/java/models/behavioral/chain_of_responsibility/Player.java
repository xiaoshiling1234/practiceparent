package models.behavioral.chain_of_responsibility;

public abstract class Player {
    private Player successor;
    public abstract void handle(int i);
    protected void setSuccessor(Player aSuccessor){
        successor=aSuccessor;
    }
    //传给下一个
    public void next(int index){
        if(successor!=null){
            successor.handle(index);
        }else {
            System.out.println("游戏结束");
        }
    }
}
