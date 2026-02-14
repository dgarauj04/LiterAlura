package com.alura.literalura.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoApi {
    private final HttpClient client = HttpClient.newHttpClient();

    public String obterDados(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("!Erro ao conectar com a API: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }

        if (response.statusCode() != 200) {
            System.err.println("!Erro na resposta da API. Status: " + response.statusCode());
            return null;
        }
        return response.body();
    }
}
