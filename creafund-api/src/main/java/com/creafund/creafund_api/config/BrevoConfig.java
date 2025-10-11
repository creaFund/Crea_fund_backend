package com.creafund.creafund_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sibApi.TransactionalEmailsApi;
import sendinblue.ApiClient;
import sendinblue.auth.ApiKeyAuth;

@Configuration
public class BrevoConfig {

    private static final Logger logger = LoggerFactory.getLogger(BrevoConfig.class);

    @Value("${brevo.api.key}")
    private String apiKey;

    @Bean
    public TransactionalEmailsApi transactionalEmailsApi() {
        // LOG DE DÉBOGAGE : Affiche la longueur de la clé API lue depuis l'environnement.
        if (apiKey != null && !apiKey.isEmpty()) {
            logger.info("Clé API Brevo chargée avec succès. Longueur de la clé : {}", apiKey.length());
        } else {
            logger.error("ERREUR CRITIQUE : La clé API Brevo (BREVO_API_KEY) est vide ou non définie dans l'environnement !");
        }

        ApiClient defaultClient = new ApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);
        return new TransactionalEmailsApi(defaultClient);
    }
}
