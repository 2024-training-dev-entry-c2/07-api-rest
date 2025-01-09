package com.example.restaurant.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                        .title("Api de gestion de restaurante")
                        .description("Documentaciín interactiva de la API RESTful para gestion de un restaurante")
                        .version("1.0.0")
                        .contact(new Contact()
                        .name("Equipo dedesarrollo de gestion de restaurante")
                        .email("equipo @restaurante.com"))
                );
    }
}
