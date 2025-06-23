package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.Campagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampagneRepository extends JpaRepository<Campagne, Long> {
}