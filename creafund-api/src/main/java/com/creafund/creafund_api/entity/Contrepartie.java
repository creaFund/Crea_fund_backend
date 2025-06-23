package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Contrepartie {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
    private double seuilMontant;
    private double quantiteDisponible;

    @ManyToOne
    private Projet projet;
}
