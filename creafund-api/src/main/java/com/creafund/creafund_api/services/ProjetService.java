package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.repository.ProjetRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjetService extends CrudServiceImpl<Projet, Long> {

    public ProjetService(ProjetRepository repository) {
        super(repository);
    }
}