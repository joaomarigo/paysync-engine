package com.portfolio.reconciliationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.reconciliationservice.model.PaymentEntity; // <-- É ESTA LINHA QUE FALTA!

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    // O Spring Data JPA é inteligente. Só de escrevermos este nome de método,
    // ele já cria a query SQL para buscar uma transação pelo ID dela!
    boolean existsByTransactionId(String transactionId);

    // O Spring cria o SQL automaticamente: SELECT * FROM tb_payments WHERE status = ?
    List<PaymentEntity> findByStatus(String status);
}