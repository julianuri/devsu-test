package com.devsu.services.impl;

import com.devsu.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestService {

    RestTemplate restTemplate;

    @Autowired
    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Response sendRequest(String url) {
        Response response;

        try {
            response = restTemplate.getForObject(url, Response.class);
        } catch (Exception e) {
            log.error("Error sending request to service", e);
            return null;
        }

        return response;
    }
}
