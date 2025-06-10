package com.oxxo.scr.ms.maintance;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@EnableAutoConfiguration(exclude = {
        OAuth2ClientAutoConfiguration.class
})
public class ScrMsMaintanceApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto arranca sin errores
    }
}
