package com.devsu.services;

import com.devsu.dto.AccountDTO;
import com.devsu.dto.Response;
import com.devsu.utils.enums.SaveOperation;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface IAccountService {

    ResponseEntity<Response> saveAccount(AccountDTO account, SaveOperation operation);
    ResponseEntity<Response> getAccount(Long id);
    ResponseEntity<Response> deleteAccount(Long id);
    ResponseEntity<Response> updateAccount(AccountDTO account);
    Boolean updateBalance(Long id, BigDecimal amount);
}
