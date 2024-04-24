package com.devsu.services;

import com.devsu.dto.Response;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public interface IReportService {

    ResponseEntity<Response> getAccountsStatement(Instant initialDate, Instant finalDate, Long clientId);

}
