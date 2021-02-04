package models.structural.adapter;

//将一个类的接口变换成客户端所期待的另一种接口，
// 从而使原本因接口不匹配而无法在一起工作的两个类
// 能够在一起工作。

//源角色
public class Adaptee {
    public void specificRequest(){
        System.out.println("specificRequest");
    }
}
