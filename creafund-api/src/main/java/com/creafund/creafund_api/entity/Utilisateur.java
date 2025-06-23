package com.creafund.creafund_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
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
}
