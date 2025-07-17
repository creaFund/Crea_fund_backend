package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService extends CrudServiceImpl<Projet, Long> {


    @Autowired
    private ProjetRepository projetRepository;

    public ProjetService(ProjetRepository repository) {
        super(repository);

    }


    public List<Projet> getProjetsParUtilisateur(Long utilisateurId) {
        return projetRepository.findByCreateurId(utilisateurId);
    }
}