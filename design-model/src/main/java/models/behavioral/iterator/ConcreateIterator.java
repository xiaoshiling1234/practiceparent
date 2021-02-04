package models.behavioral.iterator;

public class ConcreateIterator implements Iterator {
    private ConcreteAggregate agg;
    private int index = 0;
    private int size = 0;

    public ConcreateIterator(ConcreteAggregate agg) {
        this.agg = agg;
        size = agg.size();
        index = 0;
    }

    @Override
    public Object next() {
        if (index<size){
            return agg.getElement(index++);
        }else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return index<size;
    }
}
