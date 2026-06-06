package com.hotelos.dashboardservice.config;
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
                title = "HotelOS - Dashboard & Analytics Service API",
                version = "1.0",
                description = "Mehmonxona holatini real vaqt rejimida (Websocket orqali) kuzatish, statistik panellar va tahliliy ma'lumotlarni taqdim etish xizmati."
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
