package 栈;

public interface StackOperation<T> {
    public void initStack();
    public void destroyStack();
    public boolean push(T e);
    public T pop();
    public T getTop();
    public boolean stackEmpty();
}
