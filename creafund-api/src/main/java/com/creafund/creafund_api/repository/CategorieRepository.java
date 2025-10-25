package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByNom(String nom);
    Optional<Categorie> findByNom(String nom);
}
