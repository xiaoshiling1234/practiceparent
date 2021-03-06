package models.structural.callback;

abstract class ThreadHolder {
    public final void run(Callback callback){
        run();
        if (callback!=null){
            callback.call();
        }
    }

    public abstract void run();
}
