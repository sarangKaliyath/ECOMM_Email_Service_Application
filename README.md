# Ecomm Email Service Application

The Email Service is a Spring Boot microservice responsible for sending transactional emails to users across the e-commerce platform. It listens for email-related events from Kafka, renders the correct HTML template, and delivers the message through SMTP using Spring Mail.

## What this service does

This service is the notification layer of the platform. It is designed to handle emails such as:

- signup welcome messages
- password reset instructions
- email verification links
- order confirmation and rejection notifications

The service receives an `EmailDto` payload from Kafka, determines the correct template renderer, builds the HTML email body, and sends it to the specified recipient.

## How it fits in the platform

The platform is split into independently deployable Spring Boot services, registered with and discovered through Eureka:

| Service | Responsibility |
|---|---|
| [Auth Service](https://github.com/sarangKaliyath/ECOMM_Auth_ServiceApplication) | Identity, tokens, sessions |
| [Profile Service](https://github.com/sarangKaliyath/ECOMM_Profile_Service_Application) | User profile data (created reactively on signup) |
| [Product Service](https://github.com/sarangKaliyath/ECOMM_Product_ServiceApplication) | Product catalog |
| [Cart Service](https://github.com/sarangKaliyath/ECOMM_Cart_Service_Application) | Shopping cart |
| [Ordering Service](https://github.com/sarangKaliyath/ECOMM_Ordering_Service_Application) | Order lifecycle |
| [Payment Service](https://github.com/sarangKaliyath/ECOMM_Payment_Gateway_Service_Application) | Payment processing |
| **Email Service** [*(this repo)*](https://github.com/sarangKaliyath/ECOMM_Email_Service_Application) | Transactional email delivery |
| [Service Discovery](https://github.com/sarangKaliyath/ECOMM_Service_Discovery_Application) | Eureka registry |

## Architecture overview

This application follows an event-driven microservice design:

1. A producer service publishes an email event to Kafka.
2. `EmailKafkaConsumer` receives the message from the `email` topic.
3. The incoming JSON payload is deserialized into an `EmailDto`.
4. `EmailTemplateFactory` selects the correct template renderer based on the `EmailTemplate` enum.
5. The renderer builds the email subject and HTML body.
6. `EmailServiceImpl` sends the message through `JavaMailSender` using SMTP.

## Main components

### Kafka consumer

`EmailKafkaConsumer` listens to the `email` Kafka topic using the `@KafkaListener` annotation.

### Email DTOs

- `EmailDto` contains the recipient address, template type, and variable map.
- `EmailContent` contains the rendered subject and body.
- `EmailTemplate` defines the supported message types.

### Template rendering

The `EmailTemplateFactory` maps each template type to a renderer implementation.

Supported templates include:

- `SIGNUP_WELCOME`
- `PASSWORD_RESET`
- `EMAIL_VERIFICATION`
- `CONFIRM_ORDER`
- `DECLINE_ORDER`

### Mail sending

`EmailServiceImpl` constructs a `MimeMessage`, attaches the HTML content, and sends it to the destination email address through the configured SMTP provider.

## Project structure

```text
src/
  main/
    java/
      com/ecomm/ecomm_email_service_application/
        consumers/           # Kafka consumers
        dtos/                # Request/response model objects
        factories/           # Template selection factory
        services/            # Business logic for email delivery
        templates/           # Template renderer interfaces and implementations
        utils/               # Supporting utilities
    resources/
      application.properties
      templates/             # HTML email templates
```

## Configuration

The service is configured through `src/main/resources/application.properties`.

Key settings:

- `spring.application.name` → application name
- `server.port` → service port (`8089`)
- `spring.kafka.bootstrap-servers` → Kafka broker address
- `spring.mail.host` / `spring.mail.port` → SMTP host and port
- `spring.mail.username` / `spring.mail.password` → sender credentials

Environment variables used by this repo:

- `SENDER_EMAIL`
- `SENDER_PASSWORD`

## Running the service

From the project root, run:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

## Build and test

```bash
./mvnw clean test
```

## Notes

- The service is a lightweight, stateless notification component.
- It is designed to be independently deployable within the larger e-commerce microservices ecosystem.
- The email content is HTML-based and template-driven, which makes it easy to extend with new workflow emails.
