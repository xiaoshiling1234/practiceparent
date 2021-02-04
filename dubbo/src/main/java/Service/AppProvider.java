package Service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class AppProvider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF.spring/provider.xml");
        context.start();
        System.in.read();
    }
}
