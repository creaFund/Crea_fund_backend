package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Prestation {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
    private double prix;

    @ManyToOne
    private TypeService typeService;

    @ManyToOne
    private Utilisateur prestataire;
}
