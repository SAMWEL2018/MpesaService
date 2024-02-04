package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StkRequest {
	@JsonProperty(value = "TransactionType")
	private String transactionType;
	@JsonProperty(value = "Amount")
	private String amount;
	@JsonProperty(value = "CallBackURL")
	private String callBackURL;
	@JsonProperty(value = "PhoneNumber")
	private String phoneNumber;
	@JsonProperty(value = "PartyA")
	private String partyA;
	@JsonProperty(value = "PartyB")
	private String partyB;
	@JsonProperty(value = "AccountReference")
	private String accountReference;
	@JsonProperty(value = "TransactionDesc")
	private String transactionDesc;
	@JsonProperty(value = "BusinessShortCode")
	private String businessShortCode;
	@JsonProperty(value = "Timestamp")
	private String timestamp;
	@JsonProperty(value = "Password")
	private String password;


}
