package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Categorie {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
}
