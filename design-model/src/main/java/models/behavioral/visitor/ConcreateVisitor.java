package models.behavioral.visitor;

public class ConcreateVisitor implements Visitor {
    @Override
    public void visit(ConcreateElement1 el1) {
        el1.operation();
    }

    @Override
    public void visit(ConcreateElement2 el2) {
        el2.operation();
    }
}
