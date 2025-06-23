package com.creafund.creafund_api.services;
import com.creafund.creafund_api.entity.Campagne;
import com.creafund.creafund_api.repository.CampagneRepository;
import org.springframework.stereotype.Service;

@Service
public class CampagneService extends CrudServiceImpl<Campagne, Long> {

    public CampagneService(CampagneRepository repository) {
        super(repository);
    }
}