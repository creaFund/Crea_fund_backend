package com.creafund.creafund_api.services;

import com.creafund.creafund_api.Dto.ProjetDto;
import com.creafund.creafund_api.Dto.ContrepartieDto;
import com.creafund.creafund_api.entity.Categorie;
import com.creafund.creafund_api.entity.Contrepartie;
import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.CategorieRepository;
import com.creafund.creafund_api.repository.ProjetRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProjetService(ProjetRepository repository) {
        super(repository);
    }

    public List<Projet> getProjetsParUtilisateur(Long utilisateurId) {
        return projetRepository.findByCreateurId(utilisateurId);
    }

    public Projet creerProjetAvecContreparties(ProjetDto dto) {
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

        return projetRepository.save(projet);
    }
}
