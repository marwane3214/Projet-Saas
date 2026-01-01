# Customer Service

Microservice de gestion des clients pour une plateforme de facturation récurrente cloud.

## 🏗️ Architecture

Ce microservice suit une architecture hexagonale (DDD) avec les couches suivantes :

- **API** : Controllers REST, DTOs, gestion des exceptions
- **Application** : Services applicatifs, mappers MapStruct
- **Domain** : Entités métier, repositories (interfaces), value objects
- **Infrastructure** : Implémentation JPA, adapters, configuration

## 🛠️ Stack Technique

- **Java 21**
- **Spring Boot 3.2.0**
- **PostgreSQL**
- **JPA/Hibernate**
- **MapStruct** pour les mappings
- **Jakarta Validation**
- **OpenAPI/Swagger** pour la documentation
- **JUnit 5 + Mockito** pour les tests
- **Maven**

## 📋 Prérequis

- Java 21
- Maven 3.8+
- PostgreSQL 12+

## 🚀 Démarrage

### 1. Configuration de la base de données

Créer une base de données PostgreSQL :

```sql
CREATE DATABASE customer_db;
```

### 2. Configuration

Modifier `application.yml` avec vos paramètres de base de données :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/customer_db
    username: postgres
    password: votre_mot_de_passe
```

### 3. Exécution

```bash
mvn clean install
mvn spring-boot:run
```

L'application sera accessible sur `http://localhost:8080`

## 📚 Documentation API

Une fois l'application démarrée, accédez à la documentation Swagger :

- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **API Docs** : http://localhost:8080/api-docs

## 🔌 Endpoints

### Customers

- `POST /api/customers` - Créer un client
- `GET /api/customers` - Lister tous les clients
- `GET /api/customers/{id}` - Obtenir un client par ID
- `PUT /api/customers/{id}` - Mettre à jour un client
- `DELETE /api/customers/{id}` - Supprimer un client

### Companies

- `POST /api/companies` - Créer une entreprise
- `GET /api/companies` - Lister toutes les entreprises
- `GET /api/companies/{id}` - Obtenir une entreprise par ID
- `PUT /api/companies/{id}` - Mettre à jour une entreprise
- `DELETE /api/companies/{id}` - Supprimer une entreprise

### Contacts

- `POST /api/contacts` - Créer un contact
- `GET /api/contacts` - Lister tous les contacts
- `GET /api/contacts/{id}` - Obtenir un contact par ID
- `GET /api/contacts/customers/{customerId}` - Obtenir les contacts d'un client
- `PUT /api/contacts/{id}` - Mettre à jour un contact
- `DELETE /api/contacts/{id}` - Supprimer un contact

### Billing Addresses

- `POST /api/billing-addresses` - Créer une adresse de facturation
- `GET /api/billing-addresses` - Lister toutes les adresses
- `GET /api/billing-addresses/{id}` - Obtenir une adresse par ID
- `PUT /api/billing-addresses/{id}` - Mettre à jour une adresse
- `DELETE /api/billing-addresses/{id}` - Supprimer une adresse
- `POST /api/billing-addresses/{id}/attach/{customerId}` - Attacher une adresse à un client
- `DELETE /api/billing-addresses/{id}/detach` - Détacher une adresse d'un client

## 🧪 Tests

```bash
# Exécuter tous les tests
mvn test

# Exécuter uniquement les tests unitaires
mvn test -Dtest=*Test

# Exécuter uniquement les tests d'intégration
mvn test -Dtest=*IT
```

## 📦 Structure du Projet

```
customer-service/
├── src/
│   ├── main/
│   │   ├── java/com/customer/service/
│   │   │   ├── api/              # Couche API
│   │   │   │   ├── controllers/   # Controllers REST
│   │   │   │   ├── dto/          # Data Transfer Objects
│   │   │   │   └── exceptions/   # Exceptions personnalisées
│   │   │   ├── application/      # Couche Application
│   │   │   │   ├── services/     # Services applicatifs
│   │   │   │   └── mappers/      # Mappers MapStruct
│   │   │   ├── domain/           # Couche Domain
│   │   │   │   ├── entities/     # Entités métier
│   │   │   │   ├── repositories/ # Interfaces repositories
│   │   │   │   └── value-objects/ # Value objects
│   │   │   ├── infrastructure/   # Couche Infrastructure
│   │   │   │   ├── persistence/  # JPA, repositories, adapters
│   │   │   │   └── config/        # Configuration
│   │   │   └── CustomerServiceApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/     # Scripts SQL
│   └── test/                      # Tests
└── pom.xml
```

## 🔐 Sécurité

La sécurité JWT est configurée mais désactivée par défaut pour les endpoints Swagger. Pour activer l'authentification complète, configurez les filtres JWT dans `SecurityConfig`.

## 📝 Validation

Le service valide automatiquement :
- **Email** : Format email valide
- **Phone** : Format E.164 (ex: +33123456789)
- **VAT Number** : Format européen (ex: FR12345678901)
- **Champs obligatoires** : Validation Jakarta

## 🐛 Gestion des Erreurs

Toutes les erreurs sont gérées globalement via `GlobalExceptionHandler` :
- `ResourceNotFoundException` → 404
- `BusinessException` → 400
- `MethodArgumentNotValidException` → 400 avec détails
- `Exception` → 500

## 📊 Logging

Le logging utilise SLF4J avec Logback. Les logs sont configurés dans `application.yml`.

## 🚀 Déploiement

Pour le déploiement en production :

1. Configurez les variables d'environnement :
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `DATABASE_URL`
   - `JWT_SECRET`

2. Utilisez le profil `prod` :
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=prod
   ```

## 📄 Licence

Ce projet est un exemple de microservice pour une plateforme de facturation.

