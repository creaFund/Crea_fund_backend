package com.creafund.creafund_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Mode {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
}
