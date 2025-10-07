package com.creafund.creafund_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsConfig {

    @Bean
    public SesClient sesClient() {
        return SesClient.builder()
                // La région est extraite des propriétés de l'application
                .region(Region.EU_NORTH_1) 
                .build();
    }
}
