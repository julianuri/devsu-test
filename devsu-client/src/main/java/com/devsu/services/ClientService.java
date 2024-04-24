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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ClientService implements IClientService {

    private static final String CLIENT = "client";

    ClientRepository clientRepository;

    PasswordHashUtils passwordHashUtils;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordHashUtils passwordHashUtils) {
        this.clientRepository = clientRepository;
        this.passwordHashUtils = passwordHashUtils;
    }

    public ResponseEntity<Response> saveClient(ClientDTO request, SaveOperation operation) {
        Response response = new Response();
        Client client = new Client();

        try {

            if (SaveOperation.UPDATE.equals(operation) && request.id() != null && request.status() != null) {
                Optional<Client> optionalClient = clientRepository.findById(request.id());
                if (optionalClient.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }

                client = optionalClient.get();
                client.setStatus(request.status());
            } else if (SaveOperation.UPDATE.equals(operation)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                client.setStatus(Boolean.TRUE);
            }

            client.setName(request.name());
            client.setGender(request.gender());
            client.setAge(request.age());
            client.setIdentification(request.identification());
            client.setAddress(request.address());
            client.setPhone(request.phone());
            client.setPassword(passwordHashUtils.createHash(request.password()));

            client = clientRepository.save(client);

        } catch (Exception e) {
            log.error("Error saving client", e);
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(CLIENT, client);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Response> getClient(Long id) {
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

    public ResponseEntity<Response> deleteClient(Long id) {
        Response response = new Response();

        try {
            clientRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.error("Error deleting client with id: {} caused by: {}", id, e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
