package com.devsu.controller;

import com.devsu.dto.ClientDTO;
import com.devsu.dto.Response;
import com.devsu.services.ClientReportService;
import com.devsu.services.ClientService;
import com.devsu.utils.enums.SaveOperation;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ClientReportController {

    ClientReportService clientReportService;

    @Autowired
    public ClientReportController(ClientReportService clientReportService) {
        this.clientReportService = clientReportService;
    }

    @GetMapping
    public ResponseEntity<Response> getClient(@PathParam("fechaInicial") @NonNull Instant fechaInicial,
                                              @PathParam("fechaFinal") @NonNull Instant fechaFinal,
                                              @PathParam("id") @NonNull Long id) {
        log.info("Querying client with id: {}", id);
        return clientReportService.generateReport(fechaInicial, fechaFinal, id);
    }
}
