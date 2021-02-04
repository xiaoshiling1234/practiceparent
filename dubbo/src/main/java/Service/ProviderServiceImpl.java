package Service;

//暴露接口（xml 配置方法）
public class ProviderServiceImpl implements ProviderService{
    public String sayHello(String word) {
        return "hello:"+word;
    }
}
