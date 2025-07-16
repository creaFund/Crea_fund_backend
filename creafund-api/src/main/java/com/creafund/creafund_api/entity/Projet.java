package com.creafund.creafund_api.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "utilisateur_id") // Nom de la colonne dans la table projet
    private Utilisateur createur; // <-- L'utilisateur qui a créé ce projet
}
