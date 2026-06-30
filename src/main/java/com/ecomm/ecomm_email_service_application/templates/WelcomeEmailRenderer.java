package com.ecomm.ecomm_email_service_application.templates;

import com.ecomm.ecomm_email_service_application.dtos.EmailContent;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.dtos.EmailTemplate;
import com.ecomm.ecomm_email_service_application.utils.TemplateLoader;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEmailRenderer implements EmailTemplateRenderer {

    private final TemplateLoader templateLoader;

    public WelcomeEmailRenderer(TemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
    }

    public EmailTemplate getEmailTemplate() {
        return EmailTemplate.SIGNUP_WELCOME;
    }

    public EmailContent render(EmailDto emailDto) {
        String name = emailDto.getVariables().get("name");
        String html = templateLoader.load("signup_welcome.html");
        html = html.replace("{{name}}", emailDto.getVariables().get("name"));
        return new EmailContent("Welcome to Shop Easy! 🎉", html);
    }
}
