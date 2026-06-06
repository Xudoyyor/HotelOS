package com.hotelos.housekeepingservice.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HotelOS - Housekeeping Service API",
                version = "1.0",
                description = "Xonalarni tozalash navbatlari, xodimlarga vazifalar biriktirish va tozalik holatlarini (Clean, Dirty, Inspected) boshqarish xizmati."
        )
)
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .servers(List.of(
                                new Server().url("/")
                        ));
        }
}
