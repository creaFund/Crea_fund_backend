package com.creafund.creafund_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Prestation prestation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pack that = (Pack) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
