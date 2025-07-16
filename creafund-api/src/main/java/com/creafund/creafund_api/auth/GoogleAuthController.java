package com.creafund.creafund_api.auth;

import com.creafund.creafund_api.config.JwtUtils;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtils jwtUtils;

    public GoogleAuthController(UtilisateurRepository utilisateurRepository, JwtUtils jwtUtils) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body) throws Exception {
        String idTokenString = body.get("idToken");

        if (idTokenString == null || idTokenString.isBlank()) {
            return ResponseEntity.badRequest().body("idToken requis");
        }

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("114725355343-gtmq2fn31djaie350vkig16bttq5hkaj.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            return ResponseEntity.status(401).body("Token Google invalide");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        Utilisateur utilisateur = utilisateurOpt.orElseGet(() -> {
            Utilisateur u = new Utilisateur();
            u.setEmail(email);
            u.setNom((String) payload.get("name"));
            u.setDateInscription(LocalDate.now());
            u.setStatutVerification(true);
            return utilisateurRepository.save(u);
        });

        String token = jwtUtils.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
