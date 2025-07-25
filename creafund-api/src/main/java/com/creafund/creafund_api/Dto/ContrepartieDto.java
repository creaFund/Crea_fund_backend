package com.creafund.creafund_api.Dto;

import lombok.Data;

@Data
public class ContrepartieDto {
    private String nom;
    private String description;
    private double seuilMontant;
    private double quantiteDisponible;
}
