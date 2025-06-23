package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.TypeService;
import com.creafund.creafund_api.services.TypeServiceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/typeservices")
public class TypeServiceController extends CrudController<TypeService, Long> {

    public TypeServiceController(TypeServiceService service) {
        super(service);
    }
}