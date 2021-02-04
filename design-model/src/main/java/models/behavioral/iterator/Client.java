package models.behavioral.iterator;

public class Client {
    public static void main(String[] args) {
        Aggregate agg = new ConcreteAggregate();
        agg.add("张三");
        agg.add("李四");
        agg.add("王五");
        Iterator iterator = agg.createIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
