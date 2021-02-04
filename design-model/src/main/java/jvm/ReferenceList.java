package jvm;

/**
 * @author Zeng
 * @date 2020/4/7 7:44
 */
public class ReferenceList {

    private String name;
    public Object instance = null;

    public ReferenceList(String name) {
        this.name = name;
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(name + " finalize method executed!");
    }

    public static void main(String[] args) throws InterruptedException {
        ReferenceList objA = new ReferenceList("objA");
        ReferenceList objB = new ReferenceList("objB");
        ReferenceList objC = new ReferenceList("objC");
        objA.instance = objB;
        objB.instance = objC;
//        objA = null;
        objB = null;
        objC = null;
        ReferenceList objD = new ReferenceList("objD");
        objD = null;
        System.gc();
        Thread.sleep(500);
    }

}
