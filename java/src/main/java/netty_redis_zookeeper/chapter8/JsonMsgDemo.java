package netty_redis_zookeeper.chapter8;

import org.junit.Test;

import java.io.IOException;

public class JsonMsgDemo {
    public JsonMsg buildMsg(){
        JsonMsg user = new JsonMsg();
        user.setId(1000);
        user.setContent("疯狂创客圈");
        return user;
    }

    @Test
    public void serAndDesr() throws IOException{
        JsonMsg message = buildMsg();
        String json = message.convertToJson();
        System.out.println("json:="+json);
        JsonMsg inMsg = JsonMsg.parseFromJson(json);
        System.out.println("id:="+inMsg.getId());
        System.out.println("content:="+inMsg.getContent());
    }
}
