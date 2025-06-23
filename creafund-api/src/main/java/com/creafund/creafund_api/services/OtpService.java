package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.OtpCode;
import com.creafund.creafund_api.repository.OtpCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    private final OtpCodeRepository otpCodeRepository;

    public OtpService(OtpCodeRepository otpCodeRepository) {
        this.otpCodeRepository = otpCodeRepository;
    }

    public String genererCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void enregistrerOtp(String identifiant, String code) {
        OtpCode otp = new OtpCode();
        otp.setIdentifiant(identifiant);
        otp.setCode(code);
        otp.setExpiration(LocalDateTime.now().plusMinutes(5));
        otp.setUtilise(false);
        otpCodeRepository.save(otp);
    }

    public boolean verifierCode(String identifiant, String code) {
        Optional<OtpCode> otpOpt = otpCodeRepository.findTopByIdentifiantAndUtiliseFalseOrderByExpirationDesc(identifiant);
        if (otpOpt.isPresent()) {
            OtpCode otp = otpOpt.get();
            if (otp.getCode().equals(code) && otp.getExpiration().isAfter(LocalDateTime.now())) {
                otp.setUtilise(true);
                otpCodeRepository.save(otp);
                return true;
            }
        }
        return false;
    }
}
