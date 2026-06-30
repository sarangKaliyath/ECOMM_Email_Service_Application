package com.ecomm.ecomm_email_service_application.services;

import com.ecomm.ecomm_email_service_application.dtos.EmailContent;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;

// Jakarta Mail
import com.ecomm.ecomm_email_service_application.factories.EmailTemplateFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

// Spring Mail
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateFactory factory;

    public EmailServiceImpl(JavaMailSender mailSender, EmailTemplateFactory factory) {
        this.mailSender = mailSender;
        this.factory = factory;
    }

    @Override
    public void sendEmail(EmailDto emailDto) {
        EmailContent emailContent = factory.render(emailDto);

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailDto.getTo());
            helper.setSubject(emailContent.getSubject());
            helper.setText(emailContent.getBody(), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}