package com.creafund.creafund_api.Dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjetDto {
    private String titre;
    private String description;
    private double montantNecessaire;
    private boolean avecContrepartie;
    private Long categorieId;
    private Long createurId;
    private List<ContrepartieDto> contreparties;
}
