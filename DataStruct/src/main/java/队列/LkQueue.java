package 队列;

public class LkQueue<T> implements QueueOperation<T> {
    LkNode<T> front,rear;

    public LkQueue() {
        initQueue();
    }

    @Override
    public void initQueue() {
        front=rear=new LkNode<>();
        front.next=null;
    }

    @Override
    public void destroyQueue() {
        front=rear;
    }

    @Override
    public boolean enQueue(T e) {
        LkNode<T> s = new LkNode<>();
        s.data=e;
        s.next=null;
        rear.next=s;
        rear=s;
        return false;
    }

    @Override
    public T deQueue() {
        if (front==rear){//空队
            return null;
        }
        LkNode p = front.next;
        front.next=p.next;
        if (rear==p){//最后一个节点出队
            rear=front;
        }
        return (T)p.data;
    }

    @Override
    public T getHead() {
        if (front==rear){//空队
            return null;
        }
        LkNode p = front.next;
        return (T)p.data;
    }

    @Override
    public boolean queueEmpty() {
        return front==rear;
    }
}
