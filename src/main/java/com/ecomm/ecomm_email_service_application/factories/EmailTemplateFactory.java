package com.ecomm.ecomm_email_service_application.factories;

import com.ecomm.ecomm_email_service_application.dtos.EmailContent;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.dtos.EmailTemplate;
import com.ecomm.ecomm_email_service_application.templates.EmailTemplateRenderer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EmailTemplateFactory {

    private final Map<EmailTemplate, EmailTemplateRenderer> renderers;

    public EmailTemplateFactory(List<EmailTemplateRenderer> rendererList) {
        this.renderers = rendererList.stream()
                .collect(
                        Collectors.toMap(EmailTemplateRenderer::getEmailTemplate, Function.identity())
                );
    }

    public EmailContent render(EmailDto emailDto) {
        EmailTemplateRenderer renderer = renderers.get(emailDto.getEmailTemplate());

        if (renderer == null) {
            throw new IllegalArgumentException("No template found for email signature: " + emailDto.getEmailTemplate());
        }

        return renderer.render(emailDto);
    }
}
