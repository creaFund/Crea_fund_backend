package com.creafund.creafund_api.controleur;

import com.creafund.creafund_api.services.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class CrudController<T, ID> {

    protected final CrudService<T, ID> service;

    protected CrudController(CrudService<T, ID> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<T> ajouter(@RequestBody T entity) {
        return ResponseEntity.ok(service.ajout(entity));
    }

    @GetMapping
    public ResponseEntity<?> liste() {
        return ResponseEntity.ok(service.liste());
    }


    @GetMapping("/{id}")
    public ResponseEntity<T> trouver(@PathVariable ID id) {
        return service.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> miseAJour(@RequestBody T entity, @PathVariable ID id) {
        return ResponseEntity.ok(service.miseAJour(entity, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable ID id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
