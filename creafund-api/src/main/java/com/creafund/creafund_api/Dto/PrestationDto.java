package com.creafund.creafund_api.Dto;

import lombok.Data;
import java.util.List;

@Data
public class PrestationDto {
    private String nom;
    private String description;
    private double prix;
    private Long typeServiceId;
    private Long prestataireId;
    private List<PackDto> packs;
}
