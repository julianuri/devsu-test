package com.devsu.controller;

import com.devsu.dto.Response;
import com.devsu.services.impl.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ReportController {

    ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Response> getAllAccounts(
            @RequestParam(value="fechaInicial") Instant initialDate,
            @RequestParam(value="fechaFinal") Instant finalDate,
            @RequestParam(value="cliente") Long clientId) {
        log.info("Querying accounts statements for client: {} between {} and {}", clientId, initialDate, finalDate);
        return reportService.getAccountsStatement(initialDate, finalDate, clientId);
    }
}
