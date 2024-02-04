package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 14:56
 * Project MpesaService
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private String responseCode;
    private String responseDesc;
}
