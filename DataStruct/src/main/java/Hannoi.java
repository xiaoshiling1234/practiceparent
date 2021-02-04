import java.util.Scanner;

public class Hannoi {
    //定义总步数
    static int times;

    public static void main(String[] args) {
        char A='A';
        char B='B';
        char C='C';
        System.out.println("汉诺塔游戏开始");
        System.out.println("请输入盘子数：");
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        hannoi(n,A,B,C);
        s.close();

    }

    public static void move(int disk,char M,char N){
        System.out.println(
                "第"+(++times)+"次移动,盘子"+disk
                +" "+M+"----------->"+N
        );
    }

    /**
     *
     * @param n 个盘子
     * @param A 起始点
     * @param B 借助
     * @param C 目标点
     */
    public static void hannoi(int n,char A,char B,char C){
        if (n==1){
            move(n,A,C);
        }else {
            //将A n-1个盘子借助C移到B
            hannoi(n-1,A,C,B);
            //将最大的盘子移到C
            move(n,A,C);
            //再把B的n-1个盘子移动到C
            hannoi(n-1,B,A,C);
        }
    }
}
