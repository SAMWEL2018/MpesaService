package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    @JsonProperty(value = "Value")
    private Object value;
    @JsonProperty(value = "Name")
    private String name;
}
