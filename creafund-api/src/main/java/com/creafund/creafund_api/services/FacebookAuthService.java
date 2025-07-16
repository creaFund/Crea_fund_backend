package com.creafund.creafund_api.services;

import com.creafund.creafund_api.Dto.JwtResponse;
import com.creafund.creafund_api.config.JwtUtils;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class FacebookAuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtils jwtUtils;

    public FacebookAuthService(UtilisateurRepository utilisateurRepository, JwtUtils jwtUtils) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse loginWithFacebook(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;

        Map<String, String> response = restTemplate.getForObject(url, Map.class);
        if (response == null || !response.containsKey("email")) {
            throw new RuntimeException("Token Facebook invalide ou email manquant");
        }

        String email = response.get("email");
        String name = response.get("name");

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
        Utilisateur utilisateur = utilisateurOpt.orElseGet(() -> {
            Utilisateur u = new Utilisateur();
            u.setEmail(email);
            u.setNom(name);
            u.setStatutVerification(true);
            return utilisateurRepository.save(u);
        });

        // ✅ Utilise email s’il existe, sinon l’ID
        String jwt = jwtUtils.generateToken(
                utilisateur.getEmail() != null && !utilisateur.getEmail().isEmpty()
                        ? utilisateur.getEmail()
                        : String.valueOf(utilisateur.getId())
        );

        return new JwtResponse(jwt);
    }
}
