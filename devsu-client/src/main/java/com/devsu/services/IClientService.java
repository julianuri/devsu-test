package com.devsu.services;

import com.devsu.dto.ClientDTO;
import com.devsu.dto.Response;
import com.devsu.entities.Client;
import com.devsu.utils.enums.SaveOperation;
import org.springframework.http.ResponseEntity;

public interface IClientService {

    ResponseEntity<Response> saveClient(ClientDTO client, SaveOperation operation);
    ResponseEntity<Response> getClient(Long id);
    ResponseEntity<Response> deleteClient(Long id);
}
