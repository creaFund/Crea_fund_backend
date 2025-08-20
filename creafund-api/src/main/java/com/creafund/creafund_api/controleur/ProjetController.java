package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.Dto.ProjetDto;
import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.services.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "*") // facultatif, utile pour les appels Flutter
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

    // Ajoute cette méthode pour créer un projet avec contreparties
    @PostMapping("/creer-avec-contreparties")
    public ResponseEntity<Projet> creerAvecContreparties(@RequestBody ProjetDto dto) {
        Projet projetCree = projetService.creerProjetAvecContreparties(dto);
        return ResponseEntity.ok(projetCree);
    }
}
