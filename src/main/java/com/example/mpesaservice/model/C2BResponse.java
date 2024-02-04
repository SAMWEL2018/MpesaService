package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author samwel.wafula
 * Created on 01/02/2024
 * Time 19:12
 * Project MpesaService
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_paybill")
//@IdClass(C2BTransactionId.class)
public class C2BResponse {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty(value = "TransactionType")
    private String transactionType;

   // @Id
    @JsonProperty(value = "TransID")
    private String transID;

    @JsonProperty(value = "TransTime")
    private String transTime;

    @JsonProperty(value = "TransAmount")
    private String transAmount;

    @JsonProperty(value = "BusinessShortCode")
    private String businessShortCode;

    @JsonProperty(value = "BillRefNumber")
    private String billRefNumber;

    @JsonProperty(value = "InvoiceNumber")
    private String invoiceNumber;

    @JsonProperty(value = "OrgAccountBalance")
    private String orgAccountBalance;

    @JsonProperty(value = "ThirdPartyTransID")
    private String thirdPartyTransID;

    @JsonProperty(value = "MSISDN")
    private String MSISDN;

    @JsonProperty(value = "FirstName")
    private String firstName;

    @JsonProperty(value = "MiddleName")
    private String middleName;

    @JsonProperty(value = "LastName")
    private String lastName;
    @JsonIgnore
    @Column(name = "is_payment_consumed")
    private boolean paymentConsumed=false;

}
