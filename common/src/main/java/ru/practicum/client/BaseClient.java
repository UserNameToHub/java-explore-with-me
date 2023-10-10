package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseClient {
    protected final RestTemplate rest;

    protected <T> ResponseEntity<List<T>> get(String path, @Nullable Map<String, Object> parameters, Class<T> tClass) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null, new ParameterizedTypeReference<List<T>>() {
        });
    }

    protected <T1, T2> ResponseEntity<T2> post(String path, T1 body, Class<T2> tClass) {
        return makeAndSendRequest(HttpMethod.POST, path, null, body, tClass);
    }

    private <T1, T2> ResponseEntity<T2> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String,
            Object> parameters, @Nullable T1 body, Class<T2> tClass) {
        HttpEntity<T1> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<T2> serverResponse;

        if (parameters != null) {
            serverResponse = rest.exchange(path, method, requestEntity, tClass, parameters);
        } else {
            serverResponse = rest.exchange(path, method, requestEntity, tClass);
        }

        return serverResponse;
    }

    private <T1, T2> ResponseEntity<List<T2>> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String,
            Object> parameters, @Nullable T1 body, ParameterizedTypeReference<List<T2>> responseType) {
        HttpEntity requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<List<T2>> serverResponse;

        if (parameters != null) {
            serverResponse = rest.exchange(path, method, requestEntity, responseType, parameters);
        } else {
            serverResponse = rest.exchange(path, method, requestEntity, responseType);
        }

        return serverResponse;
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
