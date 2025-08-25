package com.creafund.creafund_api.services;

import com.creafund.creafund_api.Dto.ContrepartieDto;
import com.creafund.creafund_api.Dto.ProjetDto;
import com.creafund.creafund_api.aws.S3Service;
import com.creafund.creafund_api.entity.*;
import com.creafund.creafund_api.repository.CategorieRepository;
import com.creafund.creafund_api.repository.MediaRepository;
import com.creafund.creafund_api.repository.ProjetRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetService extends CrudServiceImpl<Projet, Long> {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private S3Service s3Service;

    public ProjetService(ProjetRepository repository) {
        super(repository);
    }

    public Projet creerProjetAvecContreparties(ProjetDto dto, MultipartFile[] fichiers) throws IOException {
        Projet projet = new Projet();
        projet.setTitre(dto.getTitre());
        projet.setDescription(dto.getDescription());
        projet.setMontantNecessaire(dto.getMontantNecessaire());
        projet.setDateCreation(LocalDate.now());
        projet.setStatut(false);
        projet.setAvecContrepartie(dto.isAvecContrepartie());

        Optional<Categorie> categorieOpt = categorieRepository.findById(dto.getCategorieId());
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie introuvable");
        }
        projet.setCategorie(categorieOpt.get());

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(dto.getCreateurId());
        if (utilisateurOpt.isEmpty()) {
            throw new RuntimeException("Créateur introuvable");
        }
        projet.setCreateur(utilisateurOpt.get());

        // Contreparties
        if (dto.isAvecContrepartie() && dto.getContreparties() != null) {
            List<Contrepartie> contreparties = new ArrayList<>();
            for (ContrepartieDto cpDto : dto.getContreparties()) {
                Contrepartie cp = new Contrepartie();
                cp.setNom(cpDto.getNom());
                cp.setDescription(cpDto.getDescription());
                cp.setSeuilMontant(cpDto.getSeuilMontant());
                cp.setQuantiteDisponible(cpDto.getQuantiteDisponible());
                cp.setProjet(projet);
                contreparties.add(cp);
            }
            projet.setContreparties(contreparties);
        }

        Projet savedProjet = projetRepository.save(projet);

        if (fichiers != null && fichiers.length > 0) {
            for (MultipartFile fichier : fichiers) {
                if (!fichier.isEmpty()) {
                    // Utiliser juste le prefix = "projets/{id}"
                    String prefix = "projets/" + savedProjet.getId();

                    S3Service.S3ObjectInfo uploaded = s3Service.uploadFile(prefix, fichier, false);

                    Media media = new Media();
                    media.setFileName(fichier.getOriginalFilename());
                    media.setUrl(uploaded.url()); // URL S3 du fichier uploadé
                    media.setType(fichier.getContentType());
                    media.setProjet(savedProjet);

                    mediaRepository.save(media);
                }
            }
        }


        return savedProjet;
    }

    public List<Projet> getProjetsParUtilisateur(Long utilisateurId) {
        return projetRepository.findByCreateurId(utilisateurId);
    }

}
