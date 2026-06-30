package com.ecomm.ecomm_email_service_application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EmailDto {
    private String to;
    private EmailTemplate emailTemplate;
    private Map<String, String> variables;
}
