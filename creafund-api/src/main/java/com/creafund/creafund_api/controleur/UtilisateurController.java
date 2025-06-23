package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.services.UtilisateurService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController extends CrudController<Utilisateur, Long> {

    public UtilisateurController(UtilisateurService service) {
        super(service);
    }
}