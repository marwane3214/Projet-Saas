# Configuration de la Base de Données

## 🚀 Option 1 : Démarrer avec H2 (Recommandé pour le développement rapide)

L'application peut démarrer sans PostgreSQL en utilisant H2 (base de données en mémoire).

### Démarrer avec le profil `local` :

**Via Maven :**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**Via IntelliJ IDEA :**
1. Run → Edit Configurations
2. Dans "Active profiles", ajoutez : `local`
3. Lancez l'application

**Accès à la console H2 :**
- URL : http://localhost:8080/h2-console
- JDBC URL : `jdbc:h2:mem:customer_db`
- Username : `sa`
- Password : (vide)

⚠️ **Note** : Les données seront perdues à chaque redémarrage avec H2 (base en mémoire).

---

## 🐘 Option 2 : Utiliser PostgreSQL (Recommandé pour la production)

### 1. Installer PostgreSQL

**Windows :**
- Téléchargez depuis : https://www.postgresql.org/download/windows/
- Installez PostgreSQL avec les paramètres par défaut

**Linux (Ubuntu/Debian) :**
```bash
sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
```

**macOS :**
```bash
brew install postgresql
brew services start postgresql
```

### 2. Créer la base de données

Connectez-vous à PostgreSQL :

```bash
# Windows (si installé avec l'installateur)
psql -U postgres

# Linux/macOS
sudo -u postgres psql
```

Puis créez la base de données :

```sql
CREATE DATABASE customer_db;
\q
```

### 3. Configurer l'application

Modifiez `src/main/resources/application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/customer_db
    username: postgres
    password: votre_mot_de_passe
```

Ou utilisez des variables d'environnement :

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=votre_mot_de_passe
```

### 4. Créer le schéma

Exécutez le script SQL :

```bash
psql -U postgres -d customer_db -f src/main/resources/db/migration/V1__init_schema.sql
```

Ou laissez Hibernate créer automatiquement les tables (en changeant `ddl-auto` à `update` dans `application-dev.yml`).

### 5. Démarrer l'application

```bash
mvn spring-boot:run
```

---

## 🔧 Vérifier la connexion PostgreSQL

```bash
# Vérifier que PostgreSQL est démarré
# Windows
sc query postgresql-x64-XX

# Linux
sudo systemctl status postgresql

# macOS
brew services list
```

**Tester la connexion :**
```bash
psql -U postgres -d customer_db -h localhost -p 5432
```

---

## 📝 Profils disponibles

- **`local`** : Utilise H2 (base en mémoire) - Pas besoin de PostgreSQL
- **`dev`** : Utilise PostgreSQL avec `ddl-auto: update`
- **`prod`** : Utilise PostgreSQL avec `ddl-auto: validate` (production)

---

## ⚠️ Dépannage

### Erreur : "Connection refused"

1. Vérifiez que PostgreSQL est démarré
2. Vérifiez le port (par défaut : 5432)
3. Vérifiez les credentials dans `application.yml`

### Erreur : "Database does not exist"

Créez la base de données :
```sql
CREATE DATABASE customer_db;
```

### Utiliser H2 temporairement

Ajoutez le profil `local` pour démarrer rapidement sans PostgreSQL.

