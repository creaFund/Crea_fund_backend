package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Billet {
    @Id
    @GeneratedValue
    private Long id;

    private String numero;
    private double prix;
    private LocalDate dateEmission;

    @ManyToOne
    private GroupeBillet groupe;

    @ManyToOne
    private Utilisateur acheteur;
}
