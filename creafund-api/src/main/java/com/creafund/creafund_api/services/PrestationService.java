package com.creafund.creafund_api.services;

import com.creafund.creafund_api.Dto.PackDto;
import com.creafund.creafund_api.Dto.PrestationDto;
import com.creafund.creafund_api.aws.S3Service;
import com.creafund.creafund_api.entity.*;
import com.creafund.creafund_api.repository.MediaRepository;
import com.creafund.creafund_api.repository.PrestationRepository;
import com.creafund.creafund_api.repository.TypeServiceRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestationService extends CrudServiceImpl<Prestation, Long> {

    @Autowired
    private PrestationRepository prestationRepository;

    @Autowired
    private TypeServiceRepository typeServiceRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private S3Service s3Service;

    public PrestationService(PrestationRepository repository) {
        super(repository);
    }

    public Prestation creerPrestationAvecPacksEtMedias(PrestationDto dto, MultipartFile[] fichiers) throws IOException {
        Prestation prestation = new Prestation();
        prestation.setNom(dto.getNom());
        prestation.setDescription(dto.getDescription());
        prestation.setPrix(dto.getPrix());

        TypeService type = typeServiceRepository.findById(dto.getTypeServiceId())
                .orElseThrow(() -> new RuntimeException("TypeService introuvable"));
        prestation.setTypeService(type);

        Utilisateur user = utilisateurRepository.findById(dto.getPrestataireId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        prestation.setPrestataire(user);

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

        Prestation savedPrestation = prestationRepository.save(prestation);

        if (fichiers != null && fichiers.length > 0) {
            List<Media> mediasToAdd = new ArrayList<>();
            for (MultipartFile fichier : fichiers) {
                if (!fichier.isEmpty()) {
                    String key = "prestations/" + savedPrestation.getId() + "/" + fichier.getOriginalFilename();
                    S3Service.S3ObjectInfo uploaded = s3Service.uploadFile(key, fichier, false);

                    Media media = new Media();
                    media.setFileName(fichier.getOriginalFilename());
                    media.setUrl(uploaded.url());
                    media.setType(fichier.getContentType());
                    media.setPrestation(savedPrestation);

                    mediaRepository.save(media);
                    mediasToAdd.add(media);
                }
            }
            savedPrestation.getMedias().addAll(mediasToAdd);
            prestationRepository.save(savedPrestation);
        }

        return savedPrestation;
    }

    public List<Prestation> getPrestationByIdUtilisateurs(Long prestataireId) {
        return prestationRepository.findByPrestataireId(prestataireId);
    }
}


