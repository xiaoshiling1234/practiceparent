package models.behavioral.iterator;

public interface Aggregate {
    public void add(Object obj);
    public Iterator createIterator();
}
