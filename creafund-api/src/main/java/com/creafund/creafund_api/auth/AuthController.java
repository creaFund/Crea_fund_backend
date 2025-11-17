package com.creafund.creafund_api.auth;

import com.creafund.creafund_api.config.JwtUtils;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import com.creafund.creafund_api.services.EmailService;
import com.creafund.creafund_api.services.NotificationService;
import com.creafund.creafund_api.services.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmailService emailService;
    private final UtilisateurRepository utilisateurRepository;
    private final OtpService otpService;
    private final NotificationService notificationService;
    private final JwtUtils jwtUtils;

    public AuthController(EmailService emailService,
                          UtilisateurRepository utilisateurRepository,
                          OtpService otpService,
                          NotificationService notificationService,
                          JwtUtils jwtUtils) {
        this.emailService = emailService;
        this.utilisateurRepository = utilisateurRepository;
        this.otpService = otpService;
        this.notificationService = notificationService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/demande-otp")
    public ResponseEntity<?> demanderOtp(@RequestBody Map<String, String> body) {
        String identifiant = body.get("identifiant");
        if (identifiant == null || identifiant.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Identifiant requis (email ou téléphone)");
        }

        Optional<Utilisateur> utilisateurOpt = identifiant.contains("@")
                ? utilisateurRepository.findByEmail(identifiant)
                : utilisateurRepository.findByTel(identifiant);

        if (utilisateurOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }

        String code = otpService.genererCode();
        otpService.enregistrerOtp(identifiant, code);

        if (identifiant.contains("@")) {
            try {
                String sujet = "Votre code de vérification CreaFund";
                String contenu = String.format("Bonjour,\n\nVotre code de vérification est : %s\n\nCordialement,\nL'équipe CreaFund", code);
                emailService.sendMail(identifiant, sujet, contenu);
            } catch (Exception e) {
                // Log l'erreur pour le débogage
                e.printStackTrace(); 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors de l'envoi de l'e-mail: " + e.getMessage());
            }
        } else {
            // Supposant que notificationService envoie des SMS ou autres notifications
            notificationService.envoyerCode(identifiant, code);
        }

        return ResponseEntity.ok("Code de vérification envoyé à " + identifiant);
    }


    // ✅ 2. Vérification du code OTP + génération du JWT
    @PostMapping("/verifier-otp")
    public ResponseEntity<?> verifierOtp(@RequestBody Map<String, String> body) {
        String identifiant = body.get("identifiant");
        String code = body.get("code");

        boolean valide = otpService.verifierCode(identifiant, code);
        if (!valide) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Code invalide ou expiré");
        }

        Optional<Utilisateur> utilisateur = identifiant.contains("@")
                ? utilisateurRepository.findByEmail(identifiant)
                : utilisateurRepository.findByTel(identifiant);

        if (utilisateur.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }

        String token = jwtUtils.generateToken(identifiant);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé. Veuillez fournir un token JWT valide.");
        }

        String identifiant = (String) authentication.getPrincipal();

        Optional<Utilisateur> utilisateur = identifiant.contains("@")
                ? utilisateurRepository.findByEmail(identifiant)
                // Vous pourriez vouloir ajouter une recherche par téléphone ici aussi si c'est pertinent
                : utilisateurRepository.findByTel(identifiant);

        if (utilisateur.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable pour le token fourni.");
        }

        return ResponseEntity.ok(utilisateur.get());
    }

}
