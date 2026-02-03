package com.ecomm.ecomm_email_service_application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    private String to;
    private String subject;
    private String body;
}
