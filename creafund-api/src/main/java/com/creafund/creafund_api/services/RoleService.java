package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Role;
import com.creafund.creafund_api.repository.RoleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends CrudServiceImpl<Role, Long> {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository repository) {
        super(repository);
        this.roleRepository = repository;
    }

    /**
     * Crée un rôle uniquement si le nom n'existe pas déjà.
     * @param nom le nom du rôle
     * @return le rôle créé
     * @throws IllegalArgumentException si le nom existe déjà
     */
    public Role createRole(String nom) {
        if (roleRepository.existsByNom(nom)) {
            throw new IllegalArgumentException("Ce rôle existe déjà !");
        }
        Role role = new Role();
        role.setNom(nom);
        try {
            return roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            // Double protection en cas de concurrence
            throw new IllegalArgumentException("Ce rôle existe déjà (conflit base de données) !");
        }
    }
}
