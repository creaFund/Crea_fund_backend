package com.creafund.creafund_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String adresse;
    private String tel;
    private LocalDate dateInscription;
    private boolean statutVerification;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "createur")
    @JsonIgnore
    private Set<Projet> projets;

    @OneToMany(mappedBy = "prestataire")
    @JsonIgnore
    private Set<Prestation> prestations;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "photo_profil_id")
    private Media photoProfil;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public void setEmail(String email) {
    }

    public void setNom(String nom) {
    }

    public void setPrenom(String prenom) {
    }

    public void setMotDePasse(String motDePasse) {
    }

    public void setRoles(HashSet<Role> roles) {

    }
}
