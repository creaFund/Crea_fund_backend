package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.services.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController extends CrudController<Projet, Long> {


    @Autowired
    private ProjetService projetService;

    public ProjetController(ProjetService service) {
        super(service);
    }

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<List<Projet>> getProjetsUtilisateur(@PathVariable Long id) {
        List<Projet> projets = projetService.getProjetsParUtilisateur(id);
        return ResponseEntity.ok(projets);
    }
}