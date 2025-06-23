package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService extends CrudServiceImpl<Utilisateur, Long> {

    public UtilisateurService(UtilisateurRepository repository) {
        super(repository);
    }
}