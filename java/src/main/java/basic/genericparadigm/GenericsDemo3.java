package basic.genericparadigm;
//泛型方法
class Demo3{
    public <T> T fun(T t){
        return t;
    }
}

public class GenericsDemo3 {
    public static void main(String[] args) {
        Demo3 d = new Demo3();
        String str = d.fun("Hello");
        Integer i = d.fun(30);
        System.out.println(str);
        System.out.println(i);
    }
}
