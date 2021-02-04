package models.structural.proxy;

public class ProxySubject implements Subject {
    private Subject subject;
    public ProxySubject(Subject subject){
        this.subject=subject;
    }
    @Override
    public void request() {
        beforeRequest();
        subject.request();
        afterRequest();
    }
    private void beforeRequest(){
        System.out.println("beforeRequest");
    }
    private void afterRequest(){
        System.out.println("afterRequest");
    }
}
