package com.ecomm.ecomm_email_service_application.services;

import com.ecomm.ecomm_email_service_application.dtos.EmailDto;

public interface EmailService {
    void sendEmail(EmailDto emailDto);
}
