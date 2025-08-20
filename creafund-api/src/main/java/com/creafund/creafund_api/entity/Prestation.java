package com.creafund.creafund_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnoreProperties({"prestation", "roles"})
    private Utilisateur prestataire;

    @OneToMany(mappedBy = "prestation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pack> packs = new ArrayList<>();

}
