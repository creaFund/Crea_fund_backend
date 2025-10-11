package com.creafund.creafund_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.List;

@Service
public class EmailService {

    private final TransactionalEmailsApi apiInstance;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(TransactionalEmailsApi apiInstance) {
        this.apiInstance = apiInstance;
    }

    public void sendMail(String destinataire, String sujet, String contenu) {
        // ⚠️ Assurez-vous que cet expéditeur est configuré et validé dans votre compte Brevo.
        String expediteurEmail = "creafundmali@gmail.com";
        String expediteurNom = "CreaFund";

        logger.info("Tentative d'envoi d'e-mail via Brevo (SDK v3) à : {}", destinataire);

        SendSmtpEmailSender sender = new SendSmtpEmailSender().email(expediteurEmail).name(expediteurNom);
        SendSmtpEmailTo to = new SendSmtpEmailTo().email(destinataire);
        SendSmtpEmail email = new SendSmtpEmail()
            .sender(sender)
            .to(List.of(to))
            .subject(sujet)
            .textContent(contenu);

        try {
            CreateSmtpEmail result = apiInstance.sendTransacEmail(email);
            logger.info("E-mail envoyé avec succès via Brevo ! Message ID: {}", result.getMessageId());
        } catch (Exception e) {
            logger.error("Échec de l'envoi de l'e-mail via Brevo: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail via Brevo", e);
        }
    }
}
