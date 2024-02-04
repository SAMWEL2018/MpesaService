package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StkCallback {
    @JsonProperty(value = "MerchantRequestID")
    private String merchantRequestID;
    @JsonProperty(value = "CheckoutRequestID")
    private String checkoutRequestID;
    @JsonProperty(value = "ResultDesc")
    private String resultDesc;
    @JsonProperty(value = "ResultCode")
    private int resultCode;
    @JsonProperty(value = "CallbackMetadata")
    private CallbackMetadata callbackMetadata;
}
