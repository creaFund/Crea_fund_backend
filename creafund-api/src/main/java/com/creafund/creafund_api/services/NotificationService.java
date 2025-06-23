package com.creafund.creafund_api.services;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void envoyerCode(String identifiant, String code) {
        // En production : envoi via SMS ou Email
        System.out.println("Code OTP envoyé à " + identifiant + " : " + code);
    }
}
