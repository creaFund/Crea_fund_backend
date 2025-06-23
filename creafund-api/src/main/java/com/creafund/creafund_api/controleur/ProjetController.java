package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.services.ProjetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projets")
public class ProjetController extends CrudController<Projet, Long> {

    public ProjetController(ProjetService service) {
        super(service);
    }
}