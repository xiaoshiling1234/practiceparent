package Consumer;

import Consumer.annotation.ConsumerAnnotationService;
import Consumer.annotation.ConsumerConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppAnnotation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();//启动
        ConsumerAnnotationService consumerAnnotationService = context.getBean(ConsumerAnnotationService.class);
        String hello = consumerAnnotationService.doSathello("annotation"); // 调用方法
        System.out.println("result: " + hello); // 输出结果
    }
}
