package com.creafund.creafund_api.controleur;
import com.creafund.creafund_api.entity.Billet;
import com.creafund.creafund_api.services.BilletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billets")
public class BilletController extends CrudController<Billet, Long> {

    public BilletController(BilletService service) {
        super(service);
    }
}