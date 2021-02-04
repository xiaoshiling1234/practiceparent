package jvm.oom;

import java.util.ArrayList;

/**
 * Java堆溢出
 * vm args -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\xiaobing\IdeaProjects\test\practiceparent\java\src\main\java\jvm
 * jvisualvm.exe 查看文件
 */
public class HeapOOM {
    static class OOMObject{}
    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
