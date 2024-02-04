package com.example.mpesaservice.service;

import com.example.mpesaservice.HttpHandler.HttpService;
import com.example.mpesaservice.config.AppConfig;
import com.example.mpesaservice.config.TransactionType;
import com.example.mpesaservice.model.*;
import com.example.mpesaservice.repository.C2BRepository;
import com.example.mpesaservice.repository.PayRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 09:19
 * Project MpesaService
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MpesaService {

    private final HttpService httpService;
    private final AppConfig appConfig;
    private final PayRequestRepository payRequestRepository;
    private final C2BRepository c2BRepository;

    public String generateToken() {
        log.info("token url: {}", appConfig.getToken_url());
        try {
            JsonNode jsonNode = httpService.sendRequestCall(HttpMethod.GET, appConfig.getToken_url());
            log.info("Response {}", new ObjectMapper().writeValueAsString(jsonNode));
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void processResponse(Object stkFinalResponse) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(stkFinalResponse));
        log.info(" json Node {}", node);
        JsonNode jsonNode = node.get("Body").get("stkCallback");
        log.info(" Stripped callback {}", jsonNode);
        StkCallback stkCallback = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(jsonNode), new TypeReference<>() {
        });
        log.info("stKCallback response {}", new ObjectMapper().writeValueAsString(stkCallback));
        PayRequest repo = payRequestRepository.findByMerchantRequestID(stkCallback.getMerchantRequestID());
        //PayRequest payRequest = null;
        PaymentResponse paymentResponse = null;
        if (stkCallback.getResultCode() == 0) {
            payRequestRepository.updatePayment(stkCallback.getResultCode(), stkCallback.getResultDesc(),
                    (String) stkCallback.getCallbackMetadata().getItem().get(1).getValue(), repo.getMerchantRequestID());
            paymentResponse = PaymentResponse.builder().isPaid(true).mpesaReceiptNumber((String) stkCallback.getCallbackMetadata().getItem().get(1).getValue()).ticketNo(repo.getTicketNo()).build();
        } else {
            payRequestRepository.updatePayment(stkCallback.getResultCode(), stkCallback.getResultDesc(),
                    null, repo.getMerchantRequestID());
            paymentResponse = PaymentResponse.builder().ticketNo(repo.getTicketNo()).isPaid(false).mpesaReceiptNumber(null).build();
        }
        sendBackResponseToBookingSystemCallBack(paymentResponse);
        log.info("---Callback sent to Host--");

    }

    public void sendBackResponseToBookingSystemCallBack(PaymentResponse paymentResponse) throws JsonProcessingException {
        JsonNode jsonNode = httpService.sendCallbackResponse(HttpMethod.POST, paymentResponse, appConfig.getBookingSysCallbackUrl() + paymentResponse.getTicketNo());
        CustomResponse customResponse = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(jsonNode), new TypeReference<>() {
        });
        log.info("response from CallBack send {}", new ObjectMapper().writeValueAsString(customResponse));
    }

    public CustomResponse sendStkPush(PayRequest payRequest) throws JsonProcessingException {
        if (payRequest.getPhoneNumber() != null && payRequest.getAmount() != null) {
            if (payRequest.getPhoneNumber().length() == 12) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                JsonNode jsonNode = null;
                String date = simpleDateFormat.format(new Date());
                String passwordFormat = String.format("%s%s%s", appConfig.getShortCode(), appConfig.getPassKey(), date);
                String encodedPassword = Base64.getEncoder().encodeToString(passwordFormat.getBytes(StandardCharsets.ISO_8859_1));
                StkRequest stkRequest = StkRequest.builder()
                        .businessShortCode(appConfig.getShortCode())
                        .password(encodedPassword)
                        .timestamp(date)
                        .transactionType(String.valueOf(TransactionType.CustomerBuyGoodsOnline))
                        .amount(payRequest.getAmount())
                        .partyA(payRequest.getPhoneNumber())
                        .partyB(appConfig.getPartyB())
                        .phoneNumber(payRequest.getPhoneNumber())
                        .callBackURL(appConfig.getMpesaCallBackUrl())
                        .accountReference("SPRING SPA")
                        .transactionDesc("purchase")
                        .build();
                log.info("stkRequest {}", new ObjectMapper().writeValueAsString(stkRequest));
                try {
                    jsonNode = httpService.sendRequestWithBody(HttpMethod.POST, stkRequest, appConfig.getSendStkUrl(), generateToken());
                    payRequest.setCheckoutRequestID(jsonNode.get("CheckoutRequestID").asText());
                    payRequest.setMerchantRequestID(jsonNode.get("MerchantRequestID").asText());
                    payRequest.setInitiateTransactionResponseDescription(jsonNode.get("ResponseDescription").asText());
                    payRequest.setInitiateTransactionResponseCode(jsonNode.get("ResponseCode").asText());
                    payRequestRepository.save(payRequest);
                    return CustomResponse.builder().responseCode("200").responseDesc("TRANSACTION SENT, PENDING CALLBACK RESPONSE").build();

                } catch (Exception e) {
                    log.info("Exception while sendingStkRequest {}", e.getMessage());
                    return CustomResponse.builder().responseCode("400").responseDesc("TRANSACTION NOT SENT, ERROR OCCURRED").build();
                    //throw new RuntimeException(e);
                }

            } else {
                return CustomResponse.builder().responseCode("401").responseCode("INVALID PhoneNo or Invalid PhoneNo format").build();
            }


        } else {
            return CustomResponse.builder().responseCode("401").responseCode("NULL PhoneNo or Amount").build();
        }
    }

    public Object processPayBillPayment(C2BResponse c2BResponse) {
        c2BRepository.save(c2BResponse);
        return new HashMap<Object, String>() {{
            put("ResultCode", "0");
            put("ResultDesc", "Accepted");
        }};

    }

    public Object getTransaction(String phoneNo) {
        Optional<C2BResponse> c2BResponse = c2BRepository.findByBillRefNumberAndPaymentConsumed(phoneNo, false);
        if (c2BResponse.isPresent()) {
            HashMap<Object, String> res = new HashMap<Object, String>() {
                {
                    put("phoneNumber", c2BResponse.get().getBillRefNumber());
                    put("mpesaReceiptNumber", c2BResponse.get().getTransID());
                    put("amount", c2BResponse.get().getTransAmount());
                }
            };
            c2BRepository.updateIfTicketIsPaid(true, c2BResponse.get().getTransID());
            return res;
        }
        return null;
    }
}


