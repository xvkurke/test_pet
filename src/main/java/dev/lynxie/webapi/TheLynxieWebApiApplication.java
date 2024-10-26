package dev.lynxie.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "dev.lynxie.webapi.config")
public class TheLynxieWebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TheLynxieWebApiApplication.class, args);
    }
}
