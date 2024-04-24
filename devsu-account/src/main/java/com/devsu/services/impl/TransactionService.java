package com.devsu.services.impl;

import com.devsu.dto.Response;
import com.devsu.dto.TransactionDTO;
import com.devsu.entities.Transaction;
import com.devsu.repositories.TransactionRepository;
import com.devsu.services.ITransactionService;
import com.devsu.services.impl.AccountService;
import com.devsu.utils.enums.SaveOperation;
import com.devsu.utils.enums.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService implements ITransactionService {

    private static final String TRANSACTION = "transaction";

    TransactionRepository transactionRepository;

    AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public ResponseEntity<Response> saveTransaction(TransactionDTO request, SaveOperation operation) {
        Transaction transaction = new Transaction();
        Response response = new Response();

        try {

            if (SaveOperation.UPDATE.equals(operation) && request.id() != null) {
                Optional<Transaction> optionalTransaction = transactionRepository.findById(request.id());
                if (optionalTransaction.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }

                transaction = optionalTransaction.get();
                accountService.updateBalance(transaction.getAccountId(),
                        transaction.getAmount().negate().add(request.amount()));
            } else if (SaveOperation.UPDATE.equals(operation)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {

                Boolean updatedBalance = accountService.updateBalance(request.accountId(), request.amount());

                if (Boolean.FALSE.equals(updatedBalance)) {
                    response.setReason("Saldo no disponible");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                } else if (updatedBalance == null) {
                    response.setReason("Cuenta no encontrada");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

                }

                transaction.setAccountId(request.accountId());
            }

            transaction.setUpdateDate(Instant.now());
            transaction.setAmount(request.amount());

            if (request.amount().compareTo(BigDecimal.ZERO) < 0) {
                transaction.setType(TransactionType.CREDIT);
            } else {
                transaction.setType(TransactionType.DEBIT);
            }

            transaction = transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Error saving transaction", e);
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(TRANSACTION, transaction);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Response> getTransaction(Long id) {
        Response response = new Response();

        try {
            Optional<Transaction> transaction = transactionRepository.findById(id);

            if (transaction.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            Map<String, Object> data = new HashMap<>();
            data.put(TRANSACTION, transaction);
            response.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            log.error("Error querying for transaction with id: {} caused by: {}", id, e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    public ResponseEntity<Response> deleteTransaction(Long id) {
        Response response = new Response();

        try {

            Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
            if (optionalTransaction.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            Transaction transaction = optionalTransaction.get();
            accountService.updateBalance(transaction.getAccountId(), transaction.getAmount().negate());

            transactionRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.error("Error deleting transaction with id: {} caused by: {}", id, e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
