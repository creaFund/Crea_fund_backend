package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Evenement;
import com.creafund.creafund_api.services.EvenementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evenements")
public class EvenementController extends CrudController<Evenement, Long> {

    public EvenementController(EvenementService service) {
        super(service);
    }
}