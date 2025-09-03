package com.creafund.creafund_api.services;

import com.creafund.creafund_api.Dto.UtilisateurDto;
import com.creafund.creafund_api.aws.S3Service;
import com.creafund.creafund_api.entity.Media;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.MediaRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class UtilisateurService extends CrudServiceImpl<Utilisateur, Long> {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private S3Service s3Service;

    public UtilisateurService(UtilisateurRepository repository) {
        super(repository);
    }

    public Utilisateur creerUtilisateurAvecPhoto(UtilisateurDto dto, MultipartFile fichier) throws IOException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setMotDePasse(dto.getMotDePasse());
        utilisateur.setAdresse(dto.getAdresse());
        utilisateur.setTel(dto.getTel());
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setStatutVerification(false);

        Utilisateur savedUtilisateur = super.ajout(utilisateur);

        if (fichier != null && !fichier.isEmpty()) {
            String key = "users/" + savedUtilisateur.getId() + "/" + fichier.getOriginalFilename();
            S3Service.S3ObjectInfo uploaded = s3Service.uploadFile(key, fichier, false);

            Media media = new Media();
            media.setFileName(fichier.getOriginalFilename());
            media.setUrl(uploaded.url());
            media.setType(fichier.getContentType());

            mediaRepository.save(media);

            savedUtilisateur.setPhotoProfil(media);
            return super.ajout(savedUtilisateur);
        }

        return savedUtilisateur;
    }
}
