package models.behavioral.chain_of_responsibility;

public class Client {
    public static void main(String[] args) {
        Handler h1= new ConcreateHandler();
        Handler h2= new ConcreateHandler();
        h1.setSuccessor(h2);
        h1.handleRequest();
    }
}
