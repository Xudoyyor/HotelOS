package com.hotelos.housekeepingservice.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Markaziy broker konfiguratsiyasi (hotel.exchange).
 * Housekeeping servisi:
 *  - OBUNA: room.vacated  (check-out bo'lgan xonalar uchun tozalash vazifasi yaratadi)
 *  - NASHR: room.cleaning.started / room.cleaning.completed (reception xona holatini sinxronlaydi),
 *           dashboard.update (jonli panel)
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "hotel.exchange";
    public static final String QUEUE = "housekeeping.queue";

    public static final String RK_ROOM_VACATED = "room.vacated";
    public static final String RK_CLEANING_STARTED = "room.cleaning.started";
    public static final String RK_CLEANING_COMPLETED = "room.cleaning.completed";
    public static final String RK_DASHBOARD = "dashboard.update";

    @Bean
    public TopicExchange hotelExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue housekeepingQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding roomVacatedBinding(Queue housekeepingQueue, TopicExchange hotelExchange) {
        return BindingBuilder.bind(housekeepingQueue).to(hotelExchange).with(RK_ROOM_VACATED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
