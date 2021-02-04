package models.create.singleton;

////饿汉式
//public class Singleton {
//    private static Singleton mSingleton=new Singleton();
//    //    由于构造函数是私有的，因此该类不能被继承。
//    private Singleton(){};
//    public static Singleton getInstance(){
//        return mSingleton;
//    }
//}

//懒汉式
public class Singleton {
    private static Singleton mSingleton = null;
    //    由于构造函数是私有的，因此该类不能被继承。
    private Singleton() {
    }
    synchronized public static Singleton getInstance() {
        if (mSingleton == null) {
            mSingleton = new Singleton();
        }
        return mSingleton;
    }
}