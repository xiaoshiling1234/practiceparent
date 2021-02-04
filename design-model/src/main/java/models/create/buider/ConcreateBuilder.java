package models.create.buider;

public class ConcreateBuilder extends Builder {
    Product product = new Product();

    @Override
    public void setName() {
        product.setName("ConcreateName");
    }

    @Override
    public void setPrice() {
        product.setPrice(100);
    }

    @Override
    public Product builderProduct() {
        return product;
    }
}
