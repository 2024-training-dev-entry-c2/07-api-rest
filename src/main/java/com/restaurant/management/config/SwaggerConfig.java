package com.restaurant.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  public OpenAPI customOpenApiConfig() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Manejo de restaurante API")
          .description("Manejo de operaciones de un restaurante API")
          .version("1.0.0")
          .contact(new Contact()
            .name("author")
            .email("email@gmail.com"))
      );
  }
}
