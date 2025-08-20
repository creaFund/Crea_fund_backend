package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.Dto.PrestationDto;
import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.services.PrestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestations")
public class PrestationController {

    @Autowired
    private PrestationService prestationService;

   /* public PrestationController(PrestationService service) {
        super(service);
    }*/

    @PostMapping("/creer-avec-packs")
    public ResponseEntity<Prestation> creerAvecPacks(@RequestBody PrestationDto dto) {
        Prestation p = prestationService.creerPrestationAvecPacks(dto);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity <List<Prestation>> getPrestataireServices(@PathVariable Long presatairId){
       List<Prestation> prestations  = prestationService.getPrestationByIdUtilisateurs(presatairId);
        return ResponseEntity.ok(prestations);
    }
}
