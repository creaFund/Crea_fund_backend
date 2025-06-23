package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Paiement {
    @Id
    @GeneratedValue
    private Long id;

    private double montant;
    private LocalDateTime datePaiement;
    private String statut;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Projet projet;

    @ManyToOne
    private Mode mode;
}
