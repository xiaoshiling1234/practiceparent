package basic.genericparadigm;

//另一种实现方式,　子类直接使用具体的类型
interface Info2<T>{
    public T getVar();
}
class InfoImpl2 implements Info<String>{
    private String var;

    public InfoImpl2(String var){
        this.setVar(var);
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return this.var;
    }
}

public class GenericsDemo2 {
    public static void main(String[] args) {
        Info i=null;
        i=new InfoImpl("Hello");
        System.out.println("内容："+i.getVar());
    }
}
