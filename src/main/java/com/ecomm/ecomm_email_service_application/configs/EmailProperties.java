package com.ecomm.ecomm_email_service_application.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailProperties {

    @Value("${SENDER_EMAIL}")
    private String email;

    @Value("${SENDER_PASSWORD}")
    private String password;
}
