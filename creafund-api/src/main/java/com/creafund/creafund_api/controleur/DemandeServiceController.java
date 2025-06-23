package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.DemandeService;
import com.creafund.creafund_api.services.DemandeServiceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demandeservices")
public class DemandeServiceController extends CrudController<DemandeService, Long> {

    public DemandeServiceController(DemandeServiceService service) {
        super(service);
    }
}