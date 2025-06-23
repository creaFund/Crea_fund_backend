package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.DemandeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeServiceRepository extends JpaRepository<DemandeService, Long> {
}