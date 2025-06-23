package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findTopByIdentifiantAndUtiliseFalseOrderByExpirationDesc(String identifiant);
}
