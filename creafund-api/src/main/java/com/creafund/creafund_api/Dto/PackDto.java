package com.creafund.creafund_api.Dto;

import lombok.Data;

@Data
public class PackDto {
    private String nom;
    private String description;
    private double prix;
    private int dureeEnJours;
}
