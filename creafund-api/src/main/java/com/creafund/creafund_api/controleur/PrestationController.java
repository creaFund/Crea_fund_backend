package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.services.PrestationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prestations")
public class PrestationController extends CrudController<Prestation, Long> {
    public PrestationController(PrestationService service) {
        super(service);
    }
}

