package basic.genericparadigm;
//泛型使用中类型推导方式
//通过泛型方法返回泛型类的实例

class Info4<T extends Number>{//指定泛型的类别，只能是数字类型
    private T var;

    public T getVar() {
        return var;
    }

    public void setVar(T var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "Info4{" +
                "var=" + var +
                '}';
    }
}

public class GenericsDemo4 {
    public static void main(String[] args) {
        Info4<Integer> i = fun(30);
        System.out.println(i.getVar());
    }
    private static <T extends Number> Info4<T> fun(T param){
        Info4<T> tInfo4 = new Info4<T>();
        tInfo4.setVar(param);
        return tInfo4;
    }
}
