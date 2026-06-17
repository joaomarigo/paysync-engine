package com.portfolio.paymentingestion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.paymentingestion.service.PaymentProducer;

@RestController
@RequestMapping("/api/webhooks")
public class PaymentWebhookController {

    private final PaymentProducer paymentProducer;

    public PaymentWebhookController(PaymentProducer paymentProducer) {
        this.paymentProducer = paymentProducer;
    }

    @PostMapping("/payments")
    public ResponseEntity<String> receivePayment(@RequestBody String payload) {
        
        System.out.println("🚨 Webhook Recebido. Repassando para a mensageria...");
        
        // Chama o serviço que posta na fila
        paymentProducer.sendPaymentEvent(payload);
        
        return ResponseEntity.ok("Pagamento enfileirado com sucesso pelo PaySync!");
    }
}