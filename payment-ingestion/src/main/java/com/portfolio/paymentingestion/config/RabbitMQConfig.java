package com.portfolio.paymentingestion.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nome da nossa fila
    public static final String QUEUE_NAME = "payment.ingestion.queue";

    @Bean
    public Queue paymentQueue() {
        // O "true" significa que a fila é durável (sobrevive se o Docker reiniciar)
        return new Queue(QUEUE_NAME, true);
    }
}