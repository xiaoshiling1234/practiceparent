package Consumer;

import Service.ProviderService;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import java.security.Provider;

public class AppApi {
    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer");
        applicationConfig.setOwner("sihai");
        // 连接注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://localhost:2181");
        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<ProviderService> referenceConfig = new ReferenceConfig<ProviderService>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(ProviderService.class);
        // 和本地bean一样使用xxxService
        ProviderService providerService = referenceConfig.get();// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        providerService.sayHello("hahha ");
    }
}
