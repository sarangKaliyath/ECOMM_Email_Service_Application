package com.ecomm.ecomm_email_service_application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailContent {
    private String subject;
    private String body;
}
