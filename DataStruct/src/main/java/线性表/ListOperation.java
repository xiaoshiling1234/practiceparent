package 线性表;
public interface ListOperation<T>{
    //初始化表，构造一个空的线性表
    public void initList();
    //求表长
    public int length();
    //按值查找，返回位置
    public int locateElem(T e);
    //按位查找
    //i=数组位置-1
    public T getElem(int i);
    //在第i个位置插入
    public boolean listInsert(int i ,T e);
    //在第i个位置删除，并返回删除元素的值
    public boolean listDelete(int i);
    //输出操作
    public void printList();
    //判空操作
    public boolean empty();
    //销毁线性表
    public void destroyList();


}
