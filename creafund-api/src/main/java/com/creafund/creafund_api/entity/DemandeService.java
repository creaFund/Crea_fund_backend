package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DemandeService {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dateDemande;
    private String statut;

    @ManyToOne
    private Utilisateur client;

    @ManyToOne
    private Prestation prestation;
}
