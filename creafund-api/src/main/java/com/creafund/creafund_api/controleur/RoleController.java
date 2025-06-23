package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Role;
import com.creafund.creafund_api.services.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends CrudController<Role, Long> {
    public RoleController(RoleService service) {
        super(service);
    }
}
