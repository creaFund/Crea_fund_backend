package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.Prestation;
import com.creafund.creafund_api.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation, Long> {
    List<Prestation> findByPrestataireId(Long prestataireId);
}
