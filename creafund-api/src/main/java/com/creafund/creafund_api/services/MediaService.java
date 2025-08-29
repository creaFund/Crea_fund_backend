package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Media;
import com.creafund.creafund_api.repository.MediaRepository;
import org.springframework.stereotype.Service;

@Service
public class MediaService extends CrudServiceImpl<Media, Long> {

    public MediaService(MediaRepository repository) {
        super(repository);
    }
}