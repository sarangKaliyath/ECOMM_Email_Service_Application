package com.ecomm.ecomm_email_service_application.templates;

import com.ecomm.ecomm_email_service_application.dtos.EmailContent;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.dtos.EmailTemplate;
import com.ecomm.ecomm_email_service_application.utils.TemplateLoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailIVerificationRenderer implements EmailTemplateRenderer {

    private final TemplateLoader templateLoader;

    public EmailTemplate getEmailTemplate() {
        return EmailTemplate.EMAIL_VERIFICATION;
    }

    public EmailContent render(EmailDto emailDto) {
        String html = templateLoader.load("email_verification.html");
        html = templateLoader.replace(html, emailDto.getVariables());
        return new EmailContent("Verify EmailID", html);
    }
}
