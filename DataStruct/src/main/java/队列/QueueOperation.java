package 队列;

public interface QueueOperation<T> {
    public void initQueue();
    public void destroyQueue();

    public boolean enQueue(T e);
    public T deQueue();
    public T getHead();

    public boolean queueEmpty();
}
