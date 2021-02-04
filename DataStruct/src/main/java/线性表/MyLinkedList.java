package 线性表;

public class MyLinkedList<T>  {
    //头节点
    private LNode<T> head;

    public MyLinkedList() {
        initList();
    }
    
    public void initList() {
        this.head = new LNode<>();
    }

    public int length() {
        int i =0;
        LNode current=head;
        while (current.next!=null){
            i++;
            current=current.next;
        }
        return i;
    }

    
    public LNode locateElem(T e) {
        LNode current =head;
        while (current.next!=null&&current.data!=e){
            current=current.next;
        }
        return current;
    }

    
    public LNode getElem(int i) {
        int j=1;
        LNode current =head;
        if (i==0){
            return head;
        }
        if (i<1){
            return null;
        }
        while (current!=null&&j<=i){
            current=current.next;
            j++;
        }
        return current;
    }

    
    public boolean listInsert(int i, T e) {
        if (i < 1) {
            return false;
        }
        //待插入节点
        LNode<T> s = new LNode<>(e);
        LNode current;
        current = head;
        int j = 0;
        while (current != null && j < i - 1) {//找到i-1个节点
            current = current.next;
            j++;
        }
        if (current == null) {
            return false;
        }
        s.next = current.next;
        current.next = s;
        return true;
    }

    
    public boolean listDelete(int i) {
        try {
            LNode elem = getElem(i-1);
            elem.next=elem.next.next;
            return true;
        }catch (Exception e){
            return false;
        }
    }

    
    public void printList() {
        LNode current =head;
        while (current.next!=null){
            current=current.next;
            System.out.println(current.data);
        }
    }

    
    public boolean empty() {
        return head.next==null;
    }

    
    public void destroyList() {
        head.next=null;
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();
        linkedList.listInsert(1,2);
        linkedList.listInsert(2,3);
        linkedList.listInsert(3,4);
        linkedList.listInsert(4,5);
        linkedList.printList();
        System.out.println(linkedList.getElem(3).data);
        linkedList.listDelete(3);
        linkedList.printList();

        LNode elem = linkedList.getElem(3);
        System.out.println(elem.data);
    }
}
