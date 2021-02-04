package models.structural.decorator;

public class ConcreateComponent implements Component {
    @Override
    public void operation() {
        System.out.println("ConcreateComponent");
    }
}
