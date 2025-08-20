package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.Dto.ProjetDto;
import com.creafund.creafund_api.entity.Projet;
import com.creafund.creafund_api.services.ProjetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "*") // utile pour Flutter
public class ProjetController extends CrudController<Projet, Long> {

    @Autowired
    private ProjetService projetService;

    @Autowired
    private ObjectMapper objectMapper; // pour convertir JSON -> DTO

    public ProjetController(ProjetService service) {
        super(service);
    }

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<List<Projet>> getProjetsUtilisateur(@PathVariable Long id) {
        List<Projet> projets = projetService.getProjetsParUtilisateur(id);
        return ResponseEntity.ok(projets);
    }

    /**
     * Création projet avec ou sans contreparties + fichiers (upload S3)
     */
    @PostMapping(value = "/creer-avec-contreparties", consumes = {"multipart/form-data"})
    public ResponseEntity<Projet> creerAvecContreparties(
            @RequestPart("projet") String projetJson,         // JSON envoyé en string
            @RequestPart(value = "fichiers", required = false) MultipartFile[] fichiers
    ) throws IOException {

        // Conversion du JSON en DTO
        ProjetDto dto = objectMapper.readValue(projetJson, ProjetDto.class);

        // Appel au service
        Projet projetCree = projetService.creerProjetAvecContreparties(dto, fichiers);

        return ResponseEntity.ok(projetCree);
    }
}
