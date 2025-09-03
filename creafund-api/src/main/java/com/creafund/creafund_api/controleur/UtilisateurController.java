package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.Dto.UtilisateurDto;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.services.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController extends CrudController<Utilisateur, Long> {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ObjectMapper objectMapper;

    public UtilisateurController(UtilisateurService service) {
        super(service);
    }

    @PostMapping(value = "/creer-avec-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Utilisateur> creerAvecPhotoProfil(
            @RequestPart("utilisateur") String utilisateurJson,
            @RequestPart(value = "photoProfil", required = false) MultipartFile photoProfil
    ) throws IOException {
        UtilisateurDto dto = objectMapper.readValue(utilisateurJson, UtilisateurDto.class);
        Utilisateur createdUser = utilisateurService.creerUtilisateurAvecPhoto(dto, photoProfil);
        return ResponseEntity.ok(createdUser);
    }
}
