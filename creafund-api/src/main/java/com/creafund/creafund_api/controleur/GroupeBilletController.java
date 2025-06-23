package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.GroupeBillet;
import com.creafund.creafund_api.services.GroupeBilletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groupebillets")
public class GroupeBilletController extends CrudController<GroupeBillet, Long> {

    public GroupeBilletController(GroupeBilletService service) {
        super(service);
    }
}