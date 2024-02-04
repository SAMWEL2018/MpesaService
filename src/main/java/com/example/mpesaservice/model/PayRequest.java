package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 14:39
 * Project MpesaService
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tbl_stk_payments")
public class PayRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    @Basic
    private String amount;
    @Basic
    private String phoneNumber;
    @Basic
    private int ticketNo;
    @Basic
    @CreationTimestamp
    private Timestamp dateCreated;
    @Basic
    @Column(name = "merchant_request_id")
    @JsonProperty(value = "MerchantRequestID")
    private String merchantRequestID;
    @JsonProperty(value = "CheckoutRequestID")
    @Basic
    private String CheckoutRequestID;
    @Basic
    private String InitiateTransactionResponseCode;
    @Basic
    private String InitiateTransactionResponseDescription;
    @Basic
    @Column(name = "stk_callback_response_code")
    private int stkCallbackResponseCode;
    @Basic
    @Column(name = "stk_callback_response_desc")
    private String stkCallbackResponseDescription;
    @Basic
    @Column(name = "mpesa_receipt_number")
    private String mpesaReceiptNumber;
}
