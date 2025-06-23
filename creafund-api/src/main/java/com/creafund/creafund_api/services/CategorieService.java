package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Categorie;
import com.creafund.creafund_api.repository.CategorieRepository;
import org.springframework.stereotype.Service;

@Service
public class CategorieService extends CrudServiceImpl<Categorie, Long> {

    public CategorieService(CategorieRepository repository) {
        super(repository);
    }
}