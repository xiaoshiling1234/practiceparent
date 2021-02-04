package models.behavioral.chain_of_responsibility;

/**
 * 责任链模式的重点是在“链”上，由一条链去处理相似的请求，在链中决定谁来处理这个请求，
 * 并返回相应的结果。责任链模式的类图如图5-6所示。
 */
public abstract class Handler {
    private Handler successor;

    public abstract void handleRequest();

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
}
