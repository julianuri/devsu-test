package com.devsu.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Response {

    private String reason;
    private Map<String, Object> data;
}
