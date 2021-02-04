package Consumer.annotation;

import Service.annotation.ProviderServiceAnnotation;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Component;

@Component("annotatedConsumer")
public class ConsumerAnnotationService {
    @Reference
    private ProviderServiceAnnotation providerServiceAnnotation;

    public String doSathello(String name){
        return providerServiceAnnotation.sayHelloAnnotation(name);
    }
}
