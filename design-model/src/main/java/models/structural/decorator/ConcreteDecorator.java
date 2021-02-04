package models.structural.decorator;

public class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }

    //定义自己的方法
    private void method(){
        System.out.println("修饰");
    }

    @Override
    public void operation() {
        this.method();
        super.operation();
    }
}
