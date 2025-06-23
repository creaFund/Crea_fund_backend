# Crea_fund_backend

Le backend de **Crea Fund** permet la sauvegarde et la récupération des données dans la base de données PostgreSQL. Il fournit une API sécurisée pour l’interaction entre les applications (mobile et web) et la base de données, en offrant une **authentification OTP et sociale**.

---

## 🚀 Technologies utilisées
- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- JJWT (Authentification JWT)
- Google API Client (Authentification sociale)
- Maven

---

## 📂 Pré-requis
- Java 17 installé
- Maven installé
- PostgreSQL installé
- Un outil comme Postman ou une application cliente Flutter pour tester les endpoints

---

## ⚙️ Configuration PostgreSQL

1. Crée une base de données PostgreSQL :
```sql
CREATE DATABASE creafund;
```

2. Configure le fichier `application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/creafund
spring.datasource.username=postgres
spring.datasource.password=mot_de_passe_postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false
```

---

## 📥 Installation

1. Clone le projet :
```bash
git clone https://github.com/ton-compte/crea_fund_backend.git
cd crea_fund_backend
```

2. Installe les dépendances :
```bash
mvn clean install
```

3. Démarre le projet :
```bash
mvn spring-boot:run
```

> L'API sera disponible sur :  
> `http://localhost:8080`

---

## 🔐 Authentification OTP (Numéro ou Email)

### 1. Demander un OTP
```http
POST /api/auth/demande-otp
Content-Type: application/json

{
  "identifiant": "exemple@gmail.com"
}
```

### 2. Vérifier un OTP et recevoir un JWT
```http
POST /api/auth/verifier-otp
Content-Type: application/json

{
  "identifiant": "exemple@gmail.com",
  "code": "123456"
}
```

### 3. Accéder aux routes sécurisées
```http
GET /api/utilisateurs
Authorization: Bearer <votre_token_jwt>
```

---

## ✅ Fonctionnalités principales
- Authentification OTP par email ou téléphone
- Authentification Google (Facebook, Apple à venir)
- Sécurisation JWT des routes
- Gestion des rôles
- Architecture générique pour CRUD
- Gestion PostgreSQL

---
