package com.devsu.services;


import com.devsu.dto.Response;
import com.devsu.dto.TransactionDTO;
import com.devsu.utils.enums.SaveOperation;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {

    ResponseEntity<Response> saveTransaction(TransactionDTO client, SaveOperation operation);
    ResponseEntity<Response> getTransaction(Long id);
    ResponseEntity<Response> deleteTransaction(Long id);
}
