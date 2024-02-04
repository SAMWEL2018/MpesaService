package com.example.mpesaservice;

import com.example.mpesaservice.HttpHandler.HttpService;
import com.example.mpesaservice.config.AppConfig;
import com.example.mpesaservice.model.PayRequest;
import com.example.mpesaservice.service.MpesaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MpesaServiceApplicationTests {

    @Autowired
    private MpesaService mpesaService;

    @Autowired
    private HttpService httpService;

    @Autowired
    private AppConfig appConfig;

    @Test
    void contextLoads() {
    }


    @Test
    public void getTokenT() throws Exception {
        log.info(" methode Token Exec ---> {}", mpesaService.generateToken());
    }

    @Test
    public void sendStk() throws JsonProcessingException {
        PayRequest payRequest = PayRequest.builder().amount("10").phoneNumber("254712321806").build();
        log.info("Stl req --> {}", mpesaService.sendStkPush(payRequest));
    }
}
