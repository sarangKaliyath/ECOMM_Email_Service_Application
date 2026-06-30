package com.ecomm.ecomm_email_service_application.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TemplateLoader {
    public String load(String templateName) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/" + templateName);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load template " + templateName, e);
        }
    }
}
