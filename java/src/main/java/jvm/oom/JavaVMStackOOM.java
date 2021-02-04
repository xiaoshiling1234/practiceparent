package jvm.oom;

/**
 * 创建线程导致内存溢出异常
 * -Xss200M
 */
public class JavaVMStackOOM {
    private void dontStop(){
        while (true){}
    }
    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
