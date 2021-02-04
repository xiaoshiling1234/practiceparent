package models.behavioral.chain_of_responsibility;

public class ConcreateHandler extends Handler {
    @Override
    public void handleRequest() {
        if(getSuccessor()!=null){
            System.out.println("请求传递给"+getSuccessor());
            getSuccessor().handleRequest();
        }else {
            System.out.println("请求处理");
        }

    }
}
