package com.example.mpesaservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallbackMetadata {
    @JsonProperty(value = "Item")
    private List<ItemResponse> item;
}