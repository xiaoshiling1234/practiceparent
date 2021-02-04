package 线性表;

public class MyArrayList<T> implements ListOperation<T>{
    public int maxSize;

    private T[] myList;
    private int length;

    public MyArrayList(int maxSize) {
        this.maxSize=maxSize;
        initList();
    }

    @Override
    public void initList() {
        this.myList=(T[])new Object[maxSize];
        length=0;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public int locateElem(T e) {
        int i;
        for (i=1;i<=length;i++){
            if (myList[i-1]==e){
                return i;
            }
        }
        return 0;
    }

    @Override
    public T getElem(int i) {
        return myList[i-1];
    }

    @Override
    //n/2
    public boolean listInsert(int i, T e) {
        if (i<1||i> length+1){//判断i范围是否有效
            return false;
        }
        if (i>maxSize||length==maxSize){//存储空间已满
            return false;
        }
        for (int j=length;j>=i;i--){
            myList[j]=myList[j-1];
        }
        myList[i-1]=e;
        length++;
        return true;
    }

    @Override
    //(n-1)/2
    public boolean listDelete(int i) {
        if (i<1||i>length){
            return false;
        }
        for (int j=i;j<length;j++){
            myList[j-1]=myList[j];
        }
        length--;
        return true;
    }

    @Override
    public void printList() {
        for (int i=1 ;i<=length;i++){
            System.out.print(myList[i-1]);
        }
    }

    @Override
    public boolean empty() {
        return length==0;
    }

    @Override
    public void destroyList() {
        length=0;
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>(5);
        System.out.println(list.listInsert(1,1));
        System.out.println(list.listInsert(2,2));
        System.out.println(list.listInsert(3,3));
        System.out.println(list.listInsert(4,4));
        System.out.println(list.listInsert(5,5));
        System.out.println(list.listInsert(5,6));
        list.printList();
    }
}
