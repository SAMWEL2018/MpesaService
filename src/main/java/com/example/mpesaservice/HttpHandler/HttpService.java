package com.example.mpesaservice.HttpHandler;

import com.example.mpesaservice.config.AppConfig;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 09:21
 * Project MpesaService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HttpService {
    private final WebClient webClient;
    private final AppConfig appConfig;

    public HttpHeaders headers(String consumerKey, String consumerSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(consumerKey, consumerSecret);
        //headers.add("grant_type", "client_credentials");
        return headers;
    }

    public JsonNode sendRequestCall(HttpMethod httpMethod, String url) {

        return webClient.method(httpMethod)
                .uri(url)
                .headers(httpHeaders -> {
                            httpHeaders.setBasicAuth(appConfig.getConsumerKey(), appConfig.getConsumerSecret());
                            // httpHeaders.add("grant_type", "client_credentials");
                        }
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    public JsonNode sendRequestWithBody(HttpMethod httpMethod, Object requestBody, String url, String bearer) {

        return webClient.method(httpMethod)
                .uri(url)
                .bodyValue(requestBody)
                .headers(httpHeaders -> {
                    httpHeaders.setBearerAuth(bearer);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    public JsonNode sendCallbackResponse(HttpMethod httpMethod, Object requestBody, String url) {
        return webClient.method(httpMethod)
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }


}
