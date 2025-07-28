package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.Dto.PrestationDto;
import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.services.PrestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prestations")
public class PrestationController {

    @Autowired
    private PrestationService prestationService;

    @PostMapping("/creer-avec-packs")
    public ResponseEntity<Prestation> creerAvecPacks(@RequestBody PrestationDto dto) {
        Prestation p = prestationService.creerPrestationAvecPacks(dto);
        return ResponseEntity.ok(p);
    }
}
