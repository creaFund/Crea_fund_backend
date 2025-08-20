package com.creafund.creafund_api.services;

import com.creafund.creafund_api.Dto.PackDto;
import com.creafund.creafund_api.Dto.PrestationDto;
import com.creafund.creafund_api.entity.Pack;
import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.entity.TypeService;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.PrestationRepository;
import com.creafund.creafund_api.repository.TypeServiceRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestationService {

    @Autowired
    private PrestationRepository prestationRepository;

    @Autowired
    private TypeServiceRepository typeServiceRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Prestation> getPrestationByIdUtilisateurs(Long prestataireId){
        return prestationRepository.findByPrestataireId(prestataireId);
    }

    public Prestation creerPrestationAvecPacks(PrestationDto dto) {
        Prestation prestation = new Prestation();
        prestation.setNom(dto.getNom());
        prestation.setDescription(dto.getDescription());
        prestation.setPrix(dto.getPrix());

        // Type de service
        TypeService type = typeServiceRepository.findById(dto.getTypeServiceId())
                .orElseThrow(() -> new RuntimeException("TypeService introuvable"));
        prestation.setTypeService(type);

        // Prestataire
        Utilisateur user = utilisateurRepository.findById(dto.getPrestataireId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        prestation.setPrestataire(user);

        // Packs (facultatifs)
        if (dto.getPacks() != null && !dto.getPacks().isEmpty()) {
            List<Pack> packs = new ArrayList<>();
            for (PackDto packDto : dto.getPacks()) {
                Pack p = new Pack();
                p.setNom(packDto.getNom());
                p.setDescription(packDto.getDescription());
                p.setPrix(packDto.getPrix());
                p.setDureeEnJours(packDto.getDureeEnJours());
                p.setPrestation(prestation);
                packs.add(p);
            }
            prestation.setPacks(packs);
        }

        return prestationRepository.save(prestation);
    }
}
