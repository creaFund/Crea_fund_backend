package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.repository.PrestationRepository;
import org.springframework.stereotype.Service;

@Service
public class PrestationService extends CrudServiceImpl<Prestation, Long> {
    public PrestationService(PrestationRepository repository) {
        super(repository);
    }
}
