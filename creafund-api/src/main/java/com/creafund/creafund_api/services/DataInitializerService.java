package com.creafund.creafund_api.services;

import com.creafund.creafund_api.entity.Categorie;
import com.creafund.creafund_api.entity.Role;
import com.creafund.creafund_api.entity.TypeService;
import com.creafund.creafund_api.entity.Utilisateur;
import com.creafund.creafund_api.repository.CategorieRepository;
import com.creafund.creafund_api.repository.RoleRepository;
import com.creafund.creafund_api.repository.TypeServiceRepository;
import com.creafund.creafund_api.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class DataInitializerService {

    private final RoleRepository roleRepository;
    private final CategorieRepository categorieRepository;
    private final TypeServiceRepository typeServiceRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializerService(RoleRepository roleRepository,
                                  CategorieRepository categorieRepository,
                                  TypeServiceRepository typeServiceRepository,
                                  UtilisateurRepository utilisateurRepository,
                                  PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.categorieRepository = categorieRepository;
        this.typeServiceRepository = typeServiceRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {
        initRoles();
        initCategories();
        initTypeServices();
        initDefaultUser();
    }

    private void initRoles() {
        if (roleRepository.findByNom("Admin").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setNom("Admin");
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByNom("User").isEmpty()) {
            Role userRole = new Role();
            userRole.setNom("User");
            roleRepository.save(userRole);
        }
    }

    private void initCategories() {
        Arrays.asList("Musique", "Album", "Single").forEach(name -> {
            if (categorieRepository.findByNom(name).isEmpty()) {
                Categorie categorie = new Categorie();
                categorie.setNom(name);
                categorieRepository.save(categorie);
            }
        });
    }

    private void initTypeServices() {
        Arrays.asList("Eloge", "Visite", "Video").forEach(name -> {
            if (typeServiceRepository.findByNom(name).isEmpty()) {
                TypeService typeService = new TypeService();
                typeService.setNom(name);
                typeServiceRepository.save(typeService);
            }
        });
    }

    private void initDefaultUser() {
        String defaultEmail = "creafundmali@gmail.com";
        if (utilisateurRepository.findByEmail(defaultEmail).isEmpty()) {
            Optional<Role> adminRoleOptional = roleRepository.findByNom("Admin");
            adminRoleOptional.ifPresent(adminRole -> {
                Utilisateur adminUser = new Utilisateur();
                adminUser.setEmail(defaultEmail);
                adminUser.setNom("Admin");
                adminUser.setPrenom("CreaFund");
                adminUser.setMotDePasse(passwordEncoder.encode("1234")); // Mot de passe par défaut
                adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole))); // Assigner le rôle Admin
                utilisateurRepository.save(adminUser);
            });
        }
    }
}
