package com.creafund.creafund_api.controleur;
import com.creafund.creafund_api.entity.Categorie;
import com.creafund.creafund_api.services.CategorieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategorieController extends CrudController<Categorie, Long> {

    public CategorieController(CategorieService service) {
        super(service);
    }
}