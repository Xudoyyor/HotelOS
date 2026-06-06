package com.hotelos.dashboardservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "hotel.exchange";
    public static final String DASHBOARD_QUEUE = "dashboard.queue";

    @Bean
    public TopicExchange hotelExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue dashboardQueue() {
        return new Queue(DASHBOARD_QUEUE, true);
    }

    @Bean
    public Binding bindingDashboard(Queue dashboardQueue, TopicExchange hotelExchange) {
        // "#" belgisi hotel.exchange'ga kelgan barcha (hamma mikroservislardagi) xabarlarni ushlaydi
        return BindingBuilder.bind(dashboardQueue).to(hotelExchange).with("#");
    }
}