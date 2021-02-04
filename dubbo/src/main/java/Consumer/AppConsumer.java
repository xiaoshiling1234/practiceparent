package Consumer;

import Service.ProviderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class AppConsumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        ProviderService providerService = (ProviderService)context.getBean("providerService");
        String hello = providerService.sayHello("hello");
        System.out.println(hello);
        System.in.read();
    }
}
