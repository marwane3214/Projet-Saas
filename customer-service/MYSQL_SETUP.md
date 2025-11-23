# Configuration MySQL

## ✅ Configuration effectuée

Le projet a été configuré pour utiliser MySQL au lieu de PostgreSQL.

### Fichiers modifiés :
- `pom.xml` : Dépendance MySQL ajoutée
- `application.yml` : Configuration MySQL
- `application-dev.yml` : Configuration MySQL pour le développement
- `V1__init_schema.sql` : Script SQL adapté pour MySQL

## 🔧 Configuration actuelle

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/customer_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Variables d'environnement (optionnel)
Vous pouvez définir :
- `DB_USERNAME` : Nom d'utilisateur MySQL (défaut: `root`)
- `DB_PASSWORD` : Mot de passe MySQL (défaut: vide)

## 🚀 Démarrage

1. **Assurez-vous que MySQL est démarré**

2. **Créez la base de données** (optionnel, sera créée automatiquement avec `createDatabaseIfNotExist=true`) :
   ```sql
   CREATE DATABASE customer_db;
   ```

3. **Lancez l'application**
   - Les tables seront créées automatiquement avec `ddl-auto: update`
   - Ou exécutez manuellement le script `V1__init_schema.sql`

## 📝 Notes importantes

- **UUID** : MySQL stocke les UUIDs comme `CHAR(36)` (format string)
- **Auto-création** : La base de données sera créée automatiquement si elle n'existe pas
- **Timezone** : `serverTimezone=UTC` est nécessaire pour éviter les erreurs de timezone
- **SSL** : Désactivé par défaut pour le développement local (`useSSL=false`)

## 🔍 Vérification

Une fois l'application démarrée, vérifiez les tables :
```sql
USE customer_db;
SHOW TABLES;
```

Vous devriez voir :
- `companies`
- `customers`
- `contacts`
- `billing_addresses`

