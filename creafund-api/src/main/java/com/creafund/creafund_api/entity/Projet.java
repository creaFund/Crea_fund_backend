package com.creafund.creafund_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnoreProperties({"projets", "roles"}) // ou selon les attributs de Utilisateur à ignorer
    private Utilisateur createur; // <-- L'utilisateur qui a créé ce projet

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrepartie> contreparties = new ArrayList<>();

    private boolean avecContrepartie; // <-- indique si le projet en propose ou non

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> medias = new ArrayList<>();


}
