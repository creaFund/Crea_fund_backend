package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Contrepartie;
import com.creafund.creafund_api.services.ContrepartieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contreparties")
public class ContrepartieController extends CrudController<Contrepartie, Long> {

    public ContrepartieController(ContrepartieService service) {
        super(service);
    }
}