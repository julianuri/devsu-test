package com.devsu.controller;

import com.devsu.dto.ClientDTO;
import com.devsu.dto.Response;
import com.devsu.services.ClientService;
import com.devsu.utils.enums.SaveOperation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClientController {

    ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Response> saveClient(@RequestBody @Valid ClientDTO request) {
        log.info("Creating client: {}", request);
        return clientService.saveClient(request, SaveOperation.CREATE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getClient(@PathVariable Long id) {
        log.info("Querying client with id: {}", id);
        return clientService.getClient(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long id) {
        log.info("Deleting client with id: {}", id);
        return clientService.deleteClient(id);
    }

    @PutMapping
    public ResponseEntity<Response> updateClient(@RequestBody @Valid ClientDTO request) {
        log.info("Updating client: {}", request);
        return clientService.saveClient(request, SaveOperation.UPDATE);
    }
}
