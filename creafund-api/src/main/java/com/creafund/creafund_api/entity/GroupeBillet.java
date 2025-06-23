package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupeBillet {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private int nombreBillets;
    private double prixUnitaire;

    @ManyToOne
    private Evenement evenement;
}
