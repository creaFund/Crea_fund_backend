package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Long> {
    boolean existsByNom(String nom);
    Optional<TypeService> findByNom(String nom);
}
