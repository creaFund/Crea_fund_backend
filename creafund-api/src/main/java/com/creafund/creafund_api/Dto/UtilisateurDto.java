package com.creafund.creafund_api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UtilisateurDto {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String adresse;
    private String tel;


}
