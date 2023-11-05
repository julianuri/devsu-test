package com.devsu.controller;

import com.devsu.dto.Response;
import com.devsu.dto.TransactionDTO;
import com.devsu.services.impl.TransactionService;
import com.devsu.utils.enums.SaveOperation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/movimientos")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Response> saveTransaction(@RequestBody @Valid TransactionDTO request) {
        log.info("Creating transaction: {}", request);
        return transactionService.saveTransaction(request, SaveOperation.CREATE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getClient(@PathVariable Long id) {
        log.info("Querying transaction with id: {}", id);
        return transactionService.getTransaction(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long id) {
        log.info("Deleting transaction with id: {}", id);
        return transactionService.deleteTransaction(id);
    }

    @PutMapping
        public ResponseEntity<Response> updateClient(@RequestBody @Valid TransactionDTO request) {
        log.info("Updating transaction: {}", request);
        return transactionService.saveTransaction(request, SaveOperation.UPDATE);
    }
}
