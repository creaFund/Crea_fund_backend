package com.creafund.creafund_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

@Service
public class EmailService {

    private final SesClient sesClient;

    @Autowired
    public EmailService(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void sendMail(String destinataire, String sujet, String contenu) {
        // ⚠️ Assurez-vous que cette adresse e-mail est vérifiée dans votre compte AWS SES.
        String expediteur = "creafundmali@gmail.com"; 

        Destination destination = Destination.builder()
                .toAddresses(destinataire)
                .build();

        Content sujetContent = Content.builder()
                .data(sujet)
                .build();

        Content contenuContent = Content.builder()
                .data(contenu)
                .build();

        Body body = Body.builder()
                .text(contenuContent)
                .build();

        Message message = Message.builder()
                .subject(sujetContent)
                .body(body)
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .source(expediteur)
                .destination(destination)
                .message(message)
                .build();

        try {
            sesClient.sendEmail(request);
            System.out.println("Email sent successfully via AWS SES!");
        } catch (SesException e) {
            System.err.println("Failed to send email via AWS SES: " + e.awsErrorDetails().errorMessage());
            // Vous pouvez ajouter une gestion d'erreur plus robuste ici
        }
    }
}
