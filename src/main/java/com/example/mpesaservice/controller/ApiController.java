package com.example.mpesaservice.controller;

import com.example.mpesaservice.model.C2BResponse;
import com.example.mpesaservice.model.CustomResponse;
import com.example.mpesaservice.model.PayRequest;
import com.example.mpesaservice.service.MpesaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 14:40
 * Project MpesaService
 */
@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping(value = "/api/mpesa/v1/")
public class ApiController {

    private final MpesaService mpesaService;

    @RequestMapping(value = "/sendStkPush", method = RequestMethod.POST)
    public ResponseEntity<?> sendStkReq(@RequestBody PayRequest payRequest) throws JsonProcessingException {
        CustomResponse res = mpesaService.sendStkPush(payRequest);
        log.info("pay req {}", payRequest);
        return ResponseEntity.status(Integer.parseInt(res.getResponseCode())).body(res);
    }

    @RequestMapping(value = "/getTransaction/{phoneNo}")
    public ResponseEntity<?> getPayBillPayment(@PathVariable("phoneNo") String phoneNo) {
        Object res = mpesaService.getTransaction(phoneNo);
        log.info("Get Transaction response {}", res);
        return ResponseEntity.status(200).body(res);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> serverUp(@RequestBody Object object) throws JsonProcessingException {
        log.info("Object res from ngrok {}", new ObjectMapper().writeValueAsString(object));
        mpesaService.processResponse(object);
        return ResponseEntity.ok("callback processed successfully");
    }

    @RequestMapping(value = "/receiveConfirmation", method = RequestMethod.POST)
    public ResponseEntity<?> processConfirmationForPayment(@RequestBody C2BResponse c2BResponse) throws JsonProcessingException {
        log.info("response {}", new ObjectMapper().writeValueAsString(c2BResponse));
        Object res = mpesaService.processPayBillPayment(c2BResponse);
        return ResponseEntity.status(200).body(res);
    }
}
