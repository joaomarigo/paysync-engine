package com.portfolio.reconciliationservice.task;

import com.portfolio.reconciliationservice.model.PaymentEntity;
import com.portfolio.reconciliationservice.repository.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReconciliationTask {

    private final PaymentRepository paymentRepository;

    public ReconciliationTask(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Configurado para rodar a cada 60.000 milissegundos (1 minuto)
    @Scheduled(fixedRate = 60000)
    public void performDailyReconciliation() {
        System.out.println("🤖 [" + LocalDateTime.now() + "] Iniciando auditoria de pagamentos...");

        // 1. Busca apenas os pagamentos aprovados
        List<PaymentEntity> approvedPayments = paymentRepository.findByStatus("APPROVED");

        // 2. Faz a matemática (Como usamos BigDecimal para dinheiro, usamos o método .add())
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (PaymentEntity payment : approvedPayments) {
            totalRevenue = totalRevenue.add(payment.getAmount());
        }

        // 3. Imprime o relatório gerencial
        System.out.println("📊 --- RELATÓRIO DE CONCILIAÇÃO ---");
        System.out.println("✅ Transações Aprovadas: " + approvedPayments.size());
        System.out.println("💰 Faturamento Total: R$ " + totalRevenue);
        System.out.println("-----------------------------------");
    }
}