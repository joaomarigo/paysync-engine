package com.portfolio.reconciliationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.reconciliationservice.model.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    // O Spring Data JPA é inteligente. Só de escrevermos este nome de método,
    // ele já cria a query SQL para buscar uma transação pelo ID dela!
    boolean existsByTransactionId(String transactionId);
}