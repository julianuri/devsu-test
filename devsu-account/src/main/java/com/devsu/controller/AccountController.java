package com.devsu.controller;

import com.devsu.dto.AccountDTO;
import com.devsu.dto.Response;
import com.devsu.services.impl.AccountService;
import com.devsu.services.impl.ReportService;
import com.devsu.utils.enums.SaveOperation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cuentas")
public class AccountController {

    AccountService accountService;
    ReportService reportService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Response> saveAccount(@RequestBody @Valid AccountDTO request) {
        log.info("Creating account: {}", request);
        return accountService.saveAccount(request, SaveOperation.CREATE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getAccount(@PathVariable Long id) {
        log.info("Querying account with id: {}", id);
        return accountService.getAccount(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteAccount(@PathVariable Long id) {
        log.info("Deleting account with id: {}", id);
        return accountService.deleteAccount(id);
    }

    @PutMapping
    public ResponseEntity<Response> updateAccount(@RequestBody @Valid AccountDTO request) {
        log.info("Updating account: {}", request);
        return accountService.updateAccount(request);
    }
}
