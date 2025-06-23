package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Mode;
import com.creafund.creafund_api.repository.ModeRepository;
import org.springframework.stereotype.Service;

@Service
public class ModeService extends CrudServiceImpl<Mode, Long> {

    public ModeService(ModeRepository repository) {
        super(repository);
    }
}