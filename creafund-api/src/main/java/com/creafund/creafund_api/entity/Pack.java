package com.creafund.creafund_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pack {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
    private double prix;
    private int dureeEnJours;

    @ManyToOne
    private Prestation prestation;
}
