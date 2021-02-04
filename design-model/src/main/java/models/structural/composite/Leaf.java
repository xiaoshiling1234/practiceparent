package models.structural.composite;

public class Leaf implements Component {
    @Override
    public void operation() {
        System.out.println("Leaf");
    }
}
