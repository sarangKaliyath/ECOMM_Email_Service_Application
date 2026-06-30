package com.ecomm.ecomm_email_service_application.templates;

import com.ecomm.ecomm_email_service_application.dtos.EmailContent;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.dtos.EmailTemplate;

public interface EmailTemplateRenderer {

    EmailTemplate getEmailTemplate();

    EmailContent render(EmailDto emailDto);
}
