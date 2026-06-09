package com.hotelos.roomservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Markaziy broker (hotel.exchange) konfiguratsiyasi.
 * Room-service faqat NASHR etadi:
 *  - order.charge      (reception mehmon hisobiga qo'shadi)
 *  - dashboard.update  (jonli panel)
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "hotel.exchange";
    public static final String RK_ORDER_CHARGE = "order.charge";
    public static final String RK_DASHBOARD = "dashboard.update";

    @Bean
    public TopicExchange hotelExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}