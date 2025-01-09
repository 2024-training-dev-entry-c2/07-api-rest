package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public OpenAPI customAPI(){
        return  new OpenAPI()
                .info(
                        new Info()
                                .title("API de inventario")
                                .description("Documentcion interactiva de la api de inventario")
                                .version("1.0")
                                .contact( new Contact()
                                        .name("Equipo de Desarrollo de MTY")
                                        .email("m@gmail.com")

                                )
                );

    }
}
