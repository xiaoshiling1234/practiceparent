package models.behavioral.chain_of_responsibility;

public class DrumBeater {
    public static void main(String[] args) {
        //创建一个链
        Player player = new PlayerA(new PlayerB(new PlayerC(new PlayerD(null))));
        //击鼓三下停止
        player.handle(4);
    }
}
