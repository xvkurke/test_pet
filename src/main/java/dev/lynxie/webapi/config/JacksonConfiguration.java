package dev.lynxie.webapi.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.lynxie.webapi.utils.ZonedDateTimeDeserializer;
import dev.lynxie.webapi.utils.BigDecimalSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Configuration
public class JacksonConfiguration {
    
    @Bean
    public SimpleModule javaDateTimeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        return module;
    }
}
