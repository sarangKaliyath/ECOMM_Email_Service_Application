package com.ecomm.ecomm_email_service_application.templates;

import com.ecomm.ecomm_email_service_application.dtos.EmailContent;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.dtos.EmailTemplate;
import com.ecomm.ecomm_email_service_application.utils.TemplateLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetEmailRenderer implements EmailTemplateRenderer {

    private final TemplateLoader templateLoader;

    public EmailTemplate getEmailTemplate() {
        return EmailTemplate.PASSWORD_RESET;
    }

    @Override
    public EmailContent render(EmailDto emailDto) {

        String html = templateLoader.load("password_reset.html");
        html = templateLoader.replace(html, emailDto.getVariables());

        return new EmailContent(
                "Reset Your Password",
                html
        );
    }

}
