package com.devsu.services;


import com.devsu.dto.Response;
import com.devsu.dto.TransactionDTO;
import com.devsu.utils.enums.SaveOperation;
import org.springframework.http.ResponseEntity;

public interface IRestService {

    ResponseEntity<Response> saveTransaction(TransactionDTO client, SaveOperation operation);
}
