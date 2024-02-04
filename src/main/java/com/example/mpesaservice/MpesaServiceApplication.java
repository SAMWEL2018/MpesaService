package com.example.mpesaservice;

import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@SpringBootApplication
public class MpesaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpesaServiceApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(5000));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
