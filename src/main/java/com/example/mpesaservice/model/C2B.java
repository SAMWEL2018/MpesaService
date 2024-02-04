package com.example.mpesaservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class C2B{
	private String shortCode;
	private String confirmationURL;
	private String validationURL;
	private String responseType;
}
