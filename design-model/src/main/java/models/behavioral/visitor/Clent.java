package models.behavioral.visitor;

public class Clent {
    public static void main(String[] args) {
        ObjectStructure os = new ObjectStructure();
        os.createElements();
        ConcreateVisitor vi = new ConcreateVisitor();
        os.action(vi);
    }
}
