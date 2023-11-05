package com.devsu.services;

import com.devsu.dto.Response;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public interface IClientReportService {

    ResponseEntity<Response> generateReport(Instant initialDate, Instant finalDate, Long id);
}
