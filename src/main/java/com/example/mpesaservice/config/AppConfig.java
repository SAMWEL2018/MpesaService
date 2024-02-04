package com.example.mpesaservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 09:45
 * Project MpesaService
 */
@Data
@Configuration
public class AppConfig {

    @Value("${app.GENERATE_TOKEN_URL}")
    private String token_url;
    @Value("${app.CONSUMER_KEY}")
    private String consumerKey;
    @Value("${app.CONSUMER_SECRET}")
    private String consumerSecret;
    @Value("${app.SHORTCODE}")
    private String shortCode;
    @Value("${app.PARTY_B}")
    private String partyB;
    @Value("${app.MPESA_CALLBACK_URL}")
    private String mpesaCallBackUrl;
    @Value("${app.BOOKINGSYS_CALLBACK_URL}")
    private String bookingSysCallbackUrl;
    @Value("${app.PASSKEY}")
    private String passKey;
    @Value("${app.SEND_STK_URL}")
    private String sendStkUrl;
}
