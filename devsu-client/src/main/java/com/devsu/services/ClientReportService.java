package com.devsu.services;

import com.devsu.dto.ClientDTO;
import com.devsu.dto.Response;
import com.devsu.entities.Client;
import com.devsu.repositories.ClientRepository;
import com.devsu.utils.PasswordHashUtils;
import com.devsu.utils.enums.SaveOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ClientReportService implements IClientReportService {

    private static final String CLIENT = "client";

    ClientRepository clientRepository;

    @Autowired
    public ClientReportService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Response> generateReport(Instant initialDate, Instant finalDate, Long id) {
        Response response = new Response();

        try {
            Optional<Client> client = clientRepository.findById(id);

            if (client.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            Map<String, Object> data = new HashMap<>();
            data.put(CLIENT, client);
            response.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            log.error("Error querying for client with id: {} caused by: {}", id, e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
