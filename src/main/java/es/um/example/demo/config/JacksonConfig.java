package es.um.example.demo.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalJacksonModule;

@Configuration
public class JacksonConfig {

    @Bean
    public JsonMapperBuilderCustomizer halCustomizer() {
        return builder -> {
            // Jackson 3 utiliza un sistema de construcción por pasos (Builder)
            builder.addModule(new HalJacksonModule());
            // En SB4, muchas configuraciones son ahora inmutables por defecto
        };
    }    
}
