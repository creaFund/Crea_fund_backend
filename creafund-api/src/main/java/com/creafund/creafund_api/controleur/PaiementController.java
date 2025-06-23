package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Paiement;
import com.creafund.creafund_api.services.PaiementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController extends CrudController<Paiement, Long> {

    public PaiementController(PaiementService service) {
        super(service);
    }
}