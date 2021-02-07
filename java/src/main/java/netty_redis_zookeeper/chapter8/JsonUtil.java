package netty_redis_zookeeper.chapter8;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    static GsonBuilder gb=new GsonBuilder();
    static {
        //不需要html escape
        gb.disableHtmlEscaping();
    }
    //序列化：使用谷歌Gson将POJO转成字符串
    public static String pojoToJson(java.lang.Object obj){
        String json = gb.create().toJson(obj);
        return json;
    }
    //反序列化:使用阿里Fastjson将字符串转成POJO对象
    public static <T> T jsonToPojo(String json,Class<T> tClass){
        T t= JSONObject.parseObject(json,tClass);
        return t;
    }
}
