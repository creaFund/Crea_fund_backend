package com.creafund.creafund_api.services;


import com.creafund.creafund_api.entity.TypeService;
import com.creafund.creafund_api.repository.TypeServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceService extends CrudServiceImpl<TypeService, Long> {

    public TypeServiceService(TypeServiceRepository repository) {
        super(repository);
    }
}