package com.creafund.creafund_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailService {

    private final SesClient sesClient;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void sendMail(String destinataire, String sujet, String contenu) {
        // ⚠️ Assurez-vous que cette adresse e-mail est vérifiée dans votre compte AWS SES.
        String expediteur = "creafundmali@gmail.com";

        logger.info("Tentative d'envoi d'e-mail via AWS SES à : {}", destinataire);

        Destination destination = Destination.builder()
                .toAddresses(destinataire)
                .build();

        Content sujetContent = Content.builder().data(sujet).build();
        Body body = Body.builder().text(Content.builder().data(contenu).build()).build();
        Message message = Message.builder().subject(sujetContent).body(body).build();

        SendEmailRequest request = SendEmailRequest.builder()
                .source(expediteur)
                .destination(destination)
                .message(message)
                .build();

        try {
            SendEmailResponse response = sesClient.sendEmail(request);
            logger.info("E-mail envoyé avec succès ! Message ID: {}", response.messageId());
        } catch (SesException e) {
            logger.error("Échec de l'envoi de l'e-mail via AWS SES. Code d'erreur AWS: {} | Message: {}", 
                         e.awsErrorDetails().errorCode(), 
                         e.awsErrorDetails().errorMessage(), e);
            // Remonter l'exception pour que le contrôleur puisse la gérer
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail via SES", e);
        }
    }
}
