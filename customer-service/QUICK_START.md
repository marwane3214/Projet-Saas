# Guide de Démarrage Rapide

## 🚀 Démarrage en 5 minutes

### 1. Prérequis
- Java 21 installé
- Maven 3.8+
- (Optionnel) PostgreSQL 12+ pour la production

### 2. Option A : Démarrer avec H2 (Recommandé pour débuter rapidement)

**Aucune configuration nécessaire !** L'application peut démarrer avec H2 (base en mémoire).

```bash
# Compiler le projet
mvn clean install

# Démarrer avec le profil local (H2)
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**Dans IntelliJ IDEA :**
1. Run → Edit Configurations
2. Active profiles : `local`
3. Lancez l'application

**Console H2 :** http://localhost:8080/h2-console
- JDBC URL : `jdbc:h2:mem:customer_db`
- Username : `sa`
- Password : (vide)

⚠️ Les données seront perdues à chaque redémarrage avec H2.

### 3. Option B : Utiliser PostgreSQL

#### Configuration de la base de données

```sql
CREATE DATABASE customer_db;
```

#### Configuration

Modifiez `src/main/resources/application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/customer_db
    username: postgres
    password: votre_mot_de_passe
```

#### Compilation et exécution

```bash
# Compiler le projet
mvn clean install

# Démarrer l'application
mvn spring-boot:run
```

📖 **Voir** `SETUP_DATABASE.md` pour plus de détails sur PostgreSQL.

### 5. Accéder à l'API

- **Application** : http://localhost:8080
- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **API Docs** : http://localhost:8080/api-docs

### 6. Tester l'API

#### Créer une entreprise
```bash
curl -X POST http://localhost:8080/api/companies \
  -H "Content-Type: application/json" \
  -d '{
    "legalName": "Acme Corp",
    "vatNumber": "FR12345678901",
    "country": "France"
  }'
```

#### Créer un client
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+33123456789",
    "companyId": "VOTRE_COMPANY_ID"
  }'
```

## 🧪 Exécuter les tests

```bash
mvn test
```

## 📝 Notes

- La sécurité JWT est configurée mais désactivée pour Swagger
- Les logs sont configurés pour le développement
- Le schéma de base de données sera créé automatiquement au démarrage

