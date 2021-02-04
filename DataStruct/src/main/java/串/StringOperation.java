package 串;

public interface StringOperation {
    void init();
    //赋值
    boolean strAssign(char... c);
    //复制
    boolean strCopy(String s);
    //判空
    boolean strEmpty();
    //求串长
    int strLength();
    //清空操作
    boolean clearString();
    //销毁串
    boolean destroyString(String s);
    //串连接
    char[] concat(String s1,String s2);
    //求子串
    SqString subString(int pos, int len);
    //定位操作
    int index(String t);
    //比较操作
    int strCompare(String t);
}
