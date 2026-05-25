package com.condor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Condor RFID API")
                                .version("1.0.0")
                                .description("REST API for RFID operational management")
                                .contact(
                                        new Contact()
                                                .name("Condor TI - Alfredo López A.")
                                                .email("support@condor.local")
                                )
                );
    }
}