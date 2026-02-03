package com.ecomm.ecomm_email_service_application.consumers;

import com.ecomm.ecomm_email_service_application.configs.EmailProperties;
import com.ecomm.ecomm_email_service_application.dtos.EmailDto;
import com.ecomm.ecomm_email_service_application.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class EmailKafkaConsumer {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private ObjectMapper objectMapper;

    // Listens to messages from the "signup" topic.
    // groupId defines the consumer group this service belongs to.
    // All consumers with the same groupId share the load:
    // each message is delivered to only ONE consumer instance in this group.
    // This allows horizontal scaling of the email service without sending
    // duplicate emails.
    @KafkaListener(topics = "signup", groupId = "emailService")
    public void sendEmail(String message) {

        try {
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create an Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailProperties.getEmail(), emailProperties.getPassword());  //app password
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
