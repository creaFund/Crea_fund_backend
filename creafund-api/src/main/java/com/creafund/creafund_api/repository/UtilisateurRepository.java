package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
        Optional<Utilisateur> findByEmail(String email);
        Optional<Utilisateur> findByTel(String tel);


}