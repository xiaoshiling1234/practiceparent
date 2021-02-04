package 队列;

public class SqQueue<T> implements QueueOperation<T> {
    int maxSize;
    T[] data;
    //队头和队尾指针
    int front,rear;
    //这种方法会浪费一个存储空间，取代方案1增加size,2增加tag 插入=1 删除=0

    public SqQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void initQueue() {
        front=rear=0;
        data = (T[]) new Object[maxSize];
    }

    @Override
    public void destroyQueue() {

    }

    @Override
    public boolean enQueue(T e) {
        if ((rear+1)%maxSize==front){//队满
            return false;
        }
        data[rear]=e;//元素插入队尾
        rear=(rear+1)%maxSize;
        return true;
    }

    @Override
    public T deQueue() {
        if(rear==front) {//队空
            return null;
        }
        T x = data[front];
        front=(front+1)%maxSize;
        return x;
    }

    @Override
    public T getHead() {
        if(rear==front) {//队空
            return null;
        }
        T x = data[front];
        return x;
    }

    @Override
    public boolean queueEmpty() {
        return rear == front;
    }
}
