package Service;

import Service.annotation.DubboConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class AppAnnotation {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext contex = new AnnotationConfigApplicationContext(DubboConfiguration.class);
        contex.start();
        System.in.read();
    }
}
