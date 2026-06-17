package com.portfolio.reconciliationservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.reconciliationservice.dto.PaymentMessageDTO;
import com.portfolio.reconciliationservice.model.PaymentEntity;
import com.portfolio.reconciliationservice.repository.PaymentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentQueueConsumer {

    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper; // Classe do Spring para converter JSON em Objeto Java

    public PaymentQueueConsumer(PaymentRepository paymentRepository, ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
    }

    // Esta anotação diz ao Spring para monitorizar a fila continuamente
    @RabbitListener(queues = "payment.ingestion.queue")
    public void consumeMessage(String messageBody) {
        try {
            System.out.println("📥 Mensagem retirada da fila: " + messageBody);

            // 1. Converte o texto JSON para o nosso DTO Java
            PaymentMessageDTO dto = objectMapper.readValue(messageBody, PaymentMessageDTO.class);

            // 2. REGRA DE OURO (Idempotência): Verifica se esta transação já foi salva antes
            if (paymentRepository.existsByTransactionId(dto.getTransaction_id())) {
                System.out.println("⚠️ Transação " + dto.getTransaction_id() + " já processada anteriormente. Ignorando para evitar duplicidade.");
                return;
            }

            // 3. Converte o DTO para a Entidade do Banco de Dados
            PaymentEntity entity = new PaymentEntity();
            entity.setTransactionId(dto.getTransaction_id());
            entity.setAmount(dto.getAmount());
            entity.setCurrency(dto.getCurrency());
            entity.setStatus(dto.getStatus());
            entity.setGateway(dto.getGateway());

            // 4. Salva permanentemente no MySQL
            paymentRepository.save(entity);
            System.out.println("💾 Transação " + dto.getTransaction_id() + " salva com sucesso no MySQL!");

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar mensagem da fila: " + e.getMessage());
            // Futuramente, aqui enviaríamos para a DLQ (Dead Letter Queue)
        }
    }
}