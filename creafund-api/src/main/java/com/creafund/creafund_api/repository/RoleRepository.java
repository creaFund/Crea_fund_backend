package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByNom(String nom);
    Optional<Role> findByNom(String nom);
}
