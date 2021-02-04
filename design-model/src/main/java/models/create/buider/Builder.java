package models.create.buider;

public abstract class Builder {
    public abstract void setName();

    public abstract void setPrice();

    public abstract Product builderProduct();
}
