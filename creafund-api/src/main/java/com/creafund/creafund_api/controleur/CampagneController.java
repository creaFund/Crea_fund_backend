package com.creafund.creafund_api.controleur;


import com.creafund.creafund_api.entity.Campagne;
import com.creafund.creafund_api.services.CampagneService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campagnes")
public class CampagneController extends CrudController<Campagne, Long> {

    public CampagneController(CampagneService service) {
        super(service);
    }
}