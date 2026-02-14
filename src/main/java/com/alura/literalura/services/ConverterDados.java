package com.alura.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConverterDados {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            System.err.println("! Erro ao converter dados Json: " + e.getMessage());
            return null;
        }
    }
}
