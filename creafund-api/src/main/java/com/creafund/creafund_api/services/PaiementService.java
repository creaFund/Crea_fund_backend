package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Paiement;
import com.creafund.creafund_api.repository.PaiementRepository;
import org.springframework.stereotype.Service;

@Service
public class PaiementService extends CrudServiceImpl<Paiement, Long> {

    public PaiementService(PaiementRepository repository) {
        super(repository);
    }
}