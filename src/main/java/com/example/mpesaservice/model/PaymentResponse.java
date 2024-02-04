package com.example.mpesaservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author samwel.wafula
 * Created on 31/01/2024
 * Time 18:28
 * Project MpesaService
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String mpesaReceiptNumber;
    private int ticketNo;
    private boolean isPaid;
}
