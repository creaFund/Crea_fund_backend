package com.creafund.creafund_api.config;

import com.creafund.creafund_api.entity.Categorie;
import com.creafund.creafund_api.entity.Role;
import com.creafund.creafund_api.entity.TypeService;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.CategorieRepository;
import com.creafund.creafund_api.repository.RoleRepository;
import com.creafund.creafund_api.repository.TypeServiceRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UtilisateurRepository utilisateurRepository;
    private final CategorieRepository categorieRepository;
    private final RoleRepository roleRepository;
    private final TypeServiceRepository typeServiceRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UtilisateurRepository utilisateurRepository,
                           CategorieRepository categorieRepository,
                           RoleRepository roleRepository,
                           TypeServiceRepository typeServiceRepository,
                           PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.categorieRepository = categorieRepository;
        this.roleRepository = roleRepository;
        this.typeServiceRepository = typeServiceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Début de l'initialisation des données...");

        // Création des catégories
        List<String> categories = Arrays.asList("Album", "Mixtape", "Single", "Featuring");
        for (String nomCategorie : categories) {
            if (!categorieRepository.existsByNom(nomCategorie)) {
                Categorie categorie = new Categorie();
                categorie.setNom(nomCategorie);
                categorieRepository.save(categorie);
                logger.info("Catégorie '{}' créée.", nomCategorie);
            }
        }

        // Création des types de service
        List<String> typeServices = Arrays.asList("Musique", "Eloge", "Visite");
        for (String nomTypeService : typeServices) {
            if (!typeServiceRepository.existsByNom(nomTypeService)) {
                TypeService typeService = new TypeService();
                typeService.setNom(nomTypeService);
                typeServiceRepository.save(typeService);
                logger.info("Type de service '{}' créé.", nomTypeService);
            }
        }


        // Création des rôles
        List<String> roles = Arrays.asList("ADMIN", "USER");
        for (String nomRole : roles) {
            if (!roleRepository.existsByNom(nomRole)) {
                Role role = new Role();
                role.setNom(nomRole);
                roleRepository.save(role);
                logger.info("Rôle '{}' créé.", nomRole);
            }
        }

        // Création de l'utilisateur par défaut
        if (utilisateurRepository.count() == 0) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("creafund");
            admin.setEmail("creafundmali@gmail.com");
            admin.setMotDePasse(passwordEncoder.encode("password")); // Changez ce mot de passe !
            admin.setDateInscription(LocalDate.now());
            admin.setStatutVerification(true);
            utilisateurRepository.save(admin);
            logger.info("Utilisateur admin par défaut créé avec l'email 'creafundmali@gmail.com'.");
        }

        logger.info("Initialisation des données terminée.");
    }
}