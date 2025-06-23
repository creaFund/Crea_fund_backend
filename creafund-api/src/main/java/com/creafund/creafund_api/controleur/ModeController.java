package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Mode;
import com.creafund.creafund_api.services.ModeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modes")
public class ModeController extends CrudController<Mode, Long> {

    public ModeController(ModeService service) {
        super(service);
    }
}