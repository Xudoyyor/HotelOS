package com.hotelos.roomservice.config;
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
                title = "HotelOS - Room Management Service API",
                version = "1.0",
                description = "Mehmonxona xonalari fondi, xona turlari (Standart, Lux, Suite), narxlar va xonalarning joriy bandlik statuslarini boshqarish xizmati."
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
