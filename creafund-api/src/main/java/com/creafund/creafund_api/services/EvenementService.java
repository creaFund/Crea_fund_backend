package com.creafund.creafund_api.services;


import com.creafund.creafund_api.entity.Evenement;
import com.creafund.creafund_api.repository.EvenementRepository;
import org.springframework.stereotype.Service;

@Service
public class EvenementService extends CrudServiceImpl<Evenement, Long> {

    public EvenementService(EvenementRepository repository) {
        super(repository);
    }
}