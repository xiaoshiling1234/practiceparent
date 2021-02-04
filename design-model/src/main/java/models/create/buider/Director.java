package models.create.buider;

public class Director {
    private Builder builder=new ConcreateBuilder();
    public Product build(){
        builder.setName();
        builder.setPrice();
        return builder.builderProduct();
    }
}
