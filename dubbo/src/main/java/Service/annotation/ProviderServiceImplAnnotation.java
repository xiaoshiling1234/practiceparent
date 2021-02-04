package Service.annotation;

import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImplAnnotation implements ProviderServiceAnnotation{
    public String sayHelloAnnotation(String word) {
        return word;
    }
}
