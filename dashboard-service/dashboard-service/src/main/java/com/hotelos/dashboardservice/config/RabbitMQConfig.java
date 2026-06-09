package com.hotelos.dashboardservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Markaziy broker (hotel.exchange) konfiguratsiyasi.
 * Dashboard-service OBUNA bo'ladi: dashboard.# (barcha panel yangilanishlari).
 * JacksonJsonMessageConverter JSON xabarlarni Map'ga aylantiradi (Boot3<->Boot4 hamkorligi).
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "hotel.exchange";
    public static final String DASHBOARD_QUEUE = "dashboard.queue";
    public static final String RK_DASHBOARD_PATTERN = "dashboard.#";

    @Bean
    public TopicExchange hotelExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue dashboardQueue() {
        return new Queue(DASHBOARD_QUEUE, true);
    }

    @Bean
    public Binding bindingDashboard(Queue dashboardQueue, TopicExchange hotelExchange) {
        // Faqat dashboard.* yo'nalishidagi xabarlarni qabul qiladi (ichki event'lar emas).
        return BindingBuilder.bind(dashboardQueue).to(hotelExchange).with(RK_DASHBOARD_PATTERN);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}