package LRU;

public class Node {
    Object key;
    Object value;
    Node pre;
    Node next;

    public Node(Object key,Object value){
        this.key=key;
        this.value=value;
    }
}
