package models.behavioral.visitor;

public class ConcreateElement1 extends Element {
    @Override
    public void accept(Visitor vi) {
      vi.visit(this)  ;
    }
    public void operation(){
        System.out.println("访问元素1");
    }

}
