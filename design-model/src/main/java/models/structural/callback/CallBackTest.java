package models.structural.callback;

public class CallBackTest {
    public static void main(String[] args) {
        Callback call=()-> System.out.println("事情做完了，我要做其它事情了");
        SimpleThread simpleThread=new SimpleThread();
        simpleThread.run(call);
    }
}
