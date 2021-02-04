package 线性表;

//单链表
//增加一个头节点，简化操作
public class LNode<T>{
    T data;
    LNode next;

    public LNode() {
    }

    public LNode(T data) {
        this.data = data;
    }

}
