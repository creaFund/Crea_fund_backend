package com.creafund.creafund_api.controleur;
import com.creafund.creafund_api.entity.Media;
import com.creafund.creafund_api.services.MediaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media")
public class MediaController extends CrudController<Media, Long> {

    public MediaController(MediaService service) {
        super(service);
    }
}