package netty_redis_zookeeper.chapter8;

import lombok.Data;

@Data
public class JsonMsg {
    private int id;
    private String content;
    public String convertToJson(){
        return JsonUtil.pojoToJson(this);
    }
    public static JsonMsg parseFromJson(String json){
        return JsonUtil.jsonToPojo(json,JsonMsg.class);
    }
}
