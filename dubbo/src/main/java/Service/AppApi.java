package Service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * Api方式启动
 * api的方式调用不需要其他的配置，只需要下面的代码即可。
 * 但是需要注意，官方建议：
 * Api方式用于测试用例使用，推荐xml的方式
 */
public class AppApi {
    public static void main(String[] args) {
        ProviderService providerService = new ProviderServiceImpl();
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("provider");
        applicationConfig.setOwner("sihai");

        //链接注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://localhost:2181");
        //        registry.setUsername("aaa");
        //        registry.setPassword("bbb");
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20990);
        //        protocolConfig.setThreads(200);
        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<ProviderService> service = new ServiceConfig<ProviderService>();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setProtocol(protocolConfig);
        service.setInterface(ProviderService.class);
        service.setRef(providerService);
        service.setVersion("1.0.0");

        //        其他api
        //        org.apache.dubbo.config.ServiceConfig
        //        org.apache.dubbo.config.ReferenceConfig
        //        org.apache.dubbo.config.ProtocolConfig
        //        org.apache.dubbo.config.RegistryConfig
        //        org.apache.dubbo.config.MonitorConfig
        //        org.apache.dubbo.config.ApplicationConfig
        //        org.apache.dubbo.config.ModuleConfig
        //        org.apache.dubbo.config.ProviderConfig
        //        org.apache.dubbo.config.ConsumerConfig
        //        org.apache.dubbo.config.MethodConfig
        //        org.apache.dubbo.config.ArgumentConfig
    }
}
