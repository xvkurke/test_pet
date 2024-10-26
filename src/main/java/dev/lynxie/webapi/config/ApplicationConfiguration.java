package dev.lynxie.webapi.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
@Slf4j
public class ApplicationConfiguration {
    
    @PostConstruct
    public void init() {
        log.info("Start init");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("Spring Boot application running in UTC timezone: {}", ZonedDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZZ")
        ));
        
        log.info("End init");
    }
}
