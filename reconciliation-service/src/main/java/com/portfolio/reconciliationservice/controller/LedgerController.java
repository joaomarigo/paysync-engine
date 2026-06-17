package com.portfolio.reconciliationservice.controller;

import com.portfolio.reconciliationservice.model.PaymentEntity;
import com.portfolio.reconciliationservice.repository.PaymentRepository;
import com.portfolio.reconciliationservice.task.ReconciliationTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@CrossOrigin(origins = "*")
public class LedgerController {

    private final PaymentRepository paymentRepository;
    private final ReconciliationTask reconciliationTask; // Trazemos o robô para cá

    public LedgerController(PaymentRepository paymentRepository, ReconciliationTask reconciliationTask) {
        this.paymentRepository = paymentRepository;
        this.reconciliationTask = reconciliationTask;
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentEntity>> getAllPayments() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        return ResponseEntity.ok(payments);
    }

    // Nova rota que aciona o robô manualmente!
    @PostMapping("/reconcile")
    public ResponseEntity<String> forceReconciliation() {
        reconciliationTask.performDailyReconciliation();
        return ResponseEntity.ok("Conciliação manual executada com sucesso!");
    }
}