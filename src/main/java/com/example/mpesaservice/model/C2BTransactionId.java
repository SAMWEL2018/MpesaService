package com.example.mpesaservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author samwel.wafula
 * Created on 01/02/2024
 * Time 19:47
 * Project MpesaService
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class C2BTransactionId implements Serializable {
    private int id;
    private String TransID;


}
