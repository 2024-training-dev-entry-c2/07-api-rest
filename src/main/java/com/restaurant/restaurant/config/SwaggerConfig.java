package com.restaurant.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("API Restaurant Management")
                    .description("Documentation for the Restaurant Management API")
                    .version("1.0.0")
                    .contact(new Contact()
                            .name("Robinson Muñetón Jaramillo")
                            .email("robinjara20@gmail.com")
                            .url("https://robinsonmuneton.vercel.app/")
                    )
            );
  }
}