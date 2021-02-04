package models.create.buider;

public class BuilderDemo {
    public static void main(String[] args) {
        Director director = new Director();
        Product product = director.build();
        System.out.println(product);
    }
}
