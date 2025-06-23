package com.creafund.creafund_api.services;


import com.creafund.creafund_api.entity.Billet;
import com.creafund.creafund_api.repository.BilletRepository;
import org.springframework.stereotype.Service;

@Service
public class BilletService extends CrudServiceImpl<Billet, Long> {

    public BilletService(BilletRepository repository) {
        super(repository);
    }
}