package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Projet {
    @Id
    @GeneratedValue
    private Long id;

    private String titre;
    private String description;
    private boolean statut;
    private double montantNecessaire;
    private double montantCollecte;
    private LocalDate dateCreation;

    @ManyToOne
    private Categorie categorie;
}
