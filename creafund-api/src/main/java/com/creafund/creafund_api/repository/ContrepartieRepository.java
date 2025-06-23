package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.Contrepartie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContrepartieRepository extends JpaRepository<Contrepartie, Long> {
}