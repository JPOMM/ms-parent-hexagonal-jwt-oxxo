package pe.oxxo.documents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "pe.oxxo.documents.infrastructure.client.out")
@SpringBootApplication()
public class MsDocumentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDocumentsApplication.class, args);
    }

}

