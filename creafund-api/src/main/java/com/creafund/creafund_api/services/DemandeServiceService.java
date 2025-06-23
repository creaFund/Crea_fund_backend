package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.DemandeService;
import com.creafund.creafund_api.repository.DemandeServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class DemandeServiceService extends CrudServiceImpl<DemandeService, Long> {

    public DemandeServiceService(DemandeServiceRepository repository) {
        super(repository);
    }
}