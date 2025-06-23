package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.GroupeBillet;
import com.creafund.creafund_api.repository.GroupeBilletRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupeBilletService extends CrudServiceImpl<GroupeBillet, Long> {

    public GroupeBilletService(GroupeBilletRepository repository) {
        super(repository);
    }
}