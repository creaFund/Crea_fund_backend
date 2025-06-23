package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Evenement {
    @Id
    @GeneratedValue
    private Long id;

    private String titre;
    private String description;
    private LocalDate date;
    private String lieu;

    @ManyToOne
    private Utilisateur organisateur;
}
