package com.portfolio.paymentingestion.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.portfolio.paymentingestion.config.RabbitMQConfig;

@Service
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    public PaymentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentEvent(String payload) {
        // Envia a mensagem (JSON) para a fila que criamos
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, payload);
        System.out.println("✅ Pagamento postado na fila com sucesso!");
    }
}