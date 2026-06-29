package com.ecomm.ecomm_email_service_application.consumers;

import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.services.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public EmailKafkaConsumer(ObjectMapper objectMapper,
                              EmailService emailService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "signup", groupId = "emailService")
    public void sendEmail(String message) throws Exception {

        EmailDto dto = objectMapper.readValue(message, EmailDto.class);

        emailService.sendEmail(dto);
    }
}