package com.devsu.services.impl;

import com.devsu.dto.*;
import com.devsu.repositories.AccountRepository;
import com.devsu.repositories.TransactionRepository;
import com.devsu.services.IReportService;
import com.devsu.utils.enums.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReportService implements IReportService {

    private static final String REPORT = "report";

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    @Autowired
    public ReportService(AccountRepository accountRepository,
                         TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<Response> getAccountsStatement(Instant initialDate, Instant finalDate, Long clientId) {
        Response response = new Response();

        try {
            List<ReportProject> report = accountRepository.getReport(initialDate, finalDate, clientId);

            if (report.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }

            List<ReportDTO> reportDTOList = new ArrayList<>();

            for (ReportProject reportProject : report) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setUpdateDate(reportProject.getUpdateDate());
                reportDTO.setClient(reportProject.getClient());
                reportDTO.setId(reportProject.getId());
                reportDTO.setAccountType(AccountType.values()[Integer.parseInt(reportProject.getType())]);
                reportDTO.setInitialBalance(reportProject.getInitialBalance());
                reportDTO.setStatus(reportProject.getStatus());
                reportDTO.setAmount(reportProject.getAmount());
                reportDTO.setCurrentBalance(reportProject.getCurrentBalance());
                reportDTOList.add(reportDTO);
            }

            Map<String, Object> data = new HashMap<>();
            data.put(REPORT, reportDTOList);
            response.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            log.error("Error querying report caused by: {}", e.getMessage());
            response.setReason(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

}
