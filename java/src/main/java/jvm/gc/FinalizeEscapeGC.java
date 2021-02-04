package jvm.gc;


/**
 * 演示两点
 * 1.对象可以在被gc时自我拯救
 * 2.这种自救的机会只有一次，因为一个对象的finalize()方法最多被系统自动调用一次
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVA_HOOK=null;

    public void isAlive(){
        System.out.println("yes i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVA_HOOK=this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVA_HOOK= new FinalizeEscapeGC();
        //对象第一次成功拯救自己
        SAVA_HOOK=null;
        System.gc();
        //因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVA_HOOK!=null){
            SAVA_HOOK.isAlive();
        }else {
            System.out.println("no ,i am dead :(");
        }

        //任何一个对象的finalize()方法都只会被系统自动调用一次，
        // 如果对象面临下一次回收，它的finalize()方法不会被再次执行

        //失败的第二次自救
        SAVA_HOOK=null;
        System.gc();
        //因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVA_HOOK!=null){
            SAVA_HOOK.isAlive();
        }else {
            System.out.println("no ,i am dead :(");
        }
    }
}
