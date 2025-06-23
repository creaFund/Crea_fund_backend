package com.creafund.creafund_api.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected CrudServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T ajout(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> liste() {
        return repository.findAll();
    }

    @Override
    public Optional<T> trouverParId(ID id) {
        return repository.findById(id);
    }

    @Override
    public T miseAJour(T entity, ID id) {
        if (!repository.existsById(id)) throw new RuntimeException("ID introuvable");
        return repository.save(entity);
    }

    @Override
    public void supprimer(ID id) {
        repository.deleteById(id);
    }
}
