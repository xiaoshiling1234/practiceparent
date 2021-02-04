package 栈;

//顺序栈
public class SqStack<T> implements StackOperation<T> {
    T[] data;
    int maxSize;
    int top;
    public SqStack(int maxSize) {
        this.maxSize=maxSize;
        initStack();
    }

    @Override
    public void initStack() {
        data=(T[]) new Object[maxSize];
        top=-1;
    }

    @Override
    public void destroyStack() {
        top=-1;
    }

    @Override
    public boolean push(T e) {
        //top从0开始
        if (top==maxSize-1){//栈满
            return false;
        }
        data[++top]=e;
        return true;
    }

    @Override
    public T pop() {
        if (top==-1){//栈空
            return null;
        }
        return data[top--];
    }

    @Override
    public T getTop() {
        if (top==-1){//栈空
            return null;
        }
        return data[top];
    }

    @Override
    public boolean stackEmpty() {
        return top==-1;
    }
}
