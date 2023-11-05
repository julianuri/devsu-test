package com.devsu.services.impl;

import com.devsu.dto.AccountDTO;
import com.devsu.dto.Response;
import com.devsu.entities.Account;
import com.devsu.repositories.AccountRepository;
import com.devsu.services.IAccountService;
import com.devsu.utils.enums.SaveOperation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AccountService implements IAccountService {

    private static final String ACCOUNT = "account";

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Response> saveAccount(AccountDTO request, SaveOperation operation) {
        Response response = new Response();
        Account account = new Account();

        try {

            if (request.personId() == null || request.initialBalance() == null ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            account.setStatus(Boolean.TRUE);
            account.setInitialBalance(request.initialBalance());
            account.setCurrentBalance(request.initialBalance());
            account.setPersonId(request.personId());
            account.setType(request.type());
            account = accountRepository.save(account);

        } catch (DataIntegrityViolationException e) {
            log.error("Error saving account, user id does not exist in the database", e);
            response.setReason("El usuario a asociar no existe");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
            log.error("Error saving account", e);
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(ACCOUNT, account);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Response> getAccount(Long id) {
        Response response = new Response();

        try {
            Optional<Account> account = accountRepository.findById(id);

            if (account.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            Map<String, Object> data = new HashMap<>();
            data.put(ACCOUNT, account);
            response.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            log.error("Error querying for account with id: {} caused by: {}", id, e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    public ResponseEntity<Response> updateAccount(AccountDTO request) {
        Response response = new Response();
        Account account;

        try {

            if (request.id() != null && request.status() != null) {
                Optional<Account> optionalAccount = accountRepository.findById(request.id());
                if (optionalAccount.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }

                account = optionalAccount.get();
                account.setStatus(request.status());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            account.setType(request.type());
            account = accountRepository.save(account);

        } catch (Exception e) {
            log.error("Error saving account", e);
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(ACCOUNT, account);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Response> deleteAccount(Long id) {
        Response response = new Response();

        try {
            accountRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.error("Error deleting account with id: {} caused by: {}", id, e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @Transactional
    public Boolean updateBalance(Long id, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findByIdAndStatus(id, Boolean.TRUE);

        if (optionalAccount.isEmpty()) {
            return null;
        }

        Account account = optionalAccount.get();

        BigDecimal updatedBalance = account.getCurrentBalance().add(amount);
        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        account.setCurrentBalance(updatedBalance);
        accountRepository.save(account);
        return true;
    }
}
