package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.Dto.PrestationDto;
import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.services.PrestationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/prestations")
@CrossOrigin(origins = "*")
public class PrestationController extends CrudController<Prestation, Long> {

    @Autowired
    private PrestationService prestationService;

    @Autowired
    private ObjectMapper objectMapper;

    public PrestationController(PrestationService service) {
        super(service);
    }

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<List<Prestation>> getPrestataireServices(@PathVariable Long id){
        List<Prestation> prestations = prestationService.getPrestationByIdUtilisateurs(id);
        return ResponseEntity.ok(prestations);
    }

    @PostMapping(value = "/creer-avec-packs-et-medias", consumes = {"multipart/form-data"})
    public ResponseEntity<Prestation> creerAvecPacksEtMedias(
            @RequestPart("prestation") String prestationJson,
            @RequestPart(value = "fichiers", required = false) MultipartFile[] fichiers
    ) throws IOException {
        PrestationDto dto = objectMapper.readValue(prestationJson, PrestationDto.class);
        Prestation prestationCree = prestationService.creerPrestationAvecPacksEtMedias(dto, fichiers);
        return ResponseEntity.ok(prestationCree);
    }
}

