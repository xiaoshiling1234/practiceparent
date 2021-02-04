package models.structural.bridge;
//修正抽象化
public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor imp) {
        super(imp);
    }

    @Override
    public void operation() {
        super.operation();
        System.out.println("RefinedAbstraction");
    }
}
