package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Contrepartie;
import com.creafund.creafund_api.repository.ContrepartieRepository;
import org.springframework.stereotype.Service;

@Service
public class ContrepartieService extends CrudServiceImpl<Contrepartie, Long> {

    public ContrepartieService(ContrepartieRepository repository) {
        super(repository);
    }
}