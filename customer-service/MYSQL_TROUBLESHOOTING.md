# 🔧 Guide de dépannage MySQL

## Problème : "Connection refused" ou "Communications link failure"

### ✅ Solutions rapides

#### 1. **Vérifier que MySQL est démarré**

**Windows (Services) :**
- Appuyez sur `Win + R`
- Tapez `services.msc` et appuyez sur Entrée
- Cherchez "MySQL" dans la liste
- Si l'état est "Arrêté", cliquez droit → Démarrer

**Windows (Ligne de commande) :**
```powershell
# Vérifier les services MySQL
Get-Service -Name "*mysql*"

# Démarrer MySQL (si installé comme service)
net start MySQL80
# ou
net start MySQL
```

**IntelliJ IDEA (Database Tool) :**
- Ouvrez `View` → `Tool Windows` → `Database`
- Vérifiez votre connexion MySQL
- Testez la connexion avec le bouton "Test Connection"

#### 2. **Vérifier le port MySQL**

Par défaut, MySQL écoute sur le port **3306**.

**Vérifier si le port est utilisé :**
```powershell
netstat -an | findstr :3306
```

Si rien n'apparaît, MySQL n'est probablement pas démarré.

#### 3. **Vérifier les identifiants**

Dans `application-local.properties` ou `application.properties`, vérifiez :
```properties
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

**Options :**
- Si vous avez un mot de passe MySQL, définissez la variable d'environnement :
  ```powershell
  $env:DB_PASSWORD="votre_mot_de_passe"
  ```
- Ou modifiez directement dans `application-local.properties` :
  ```properties
  spring.datasource.password=votre_mot_de_passe
  ```

#### 4. **Tester la connexion MySQL manuellement**

**Via MySQL Command Line Client :**
```bash
mysql -u root -p
# Entrez votre mot de passe
```

**Via IntelliJ Database Tool :**
- Ouvrez `View` → `Tool Windows` → `Database`
- Ajoutez une nouvelle Data Source → MySQL
- Testez la connexion

#### 5. **Vérifier la configuration IntelliJ Run Configuration**

1. Ouvrez `Run` → `Edit Configurations...`
2. Sélectionnez `CustomerServiceApplication`
3. Vérifiez que :
   - **Active profiles:** `local` est sélectionné
   - **VM options:** `--enable-native-access=ALL-UNNAMED` est présent

#### 6. **Créer la base de données manuellement (si nécessaire)**

```sql
CREATE DATABASE IF NOT EXISTS customer_db;
```

### 🔍 Diagnostic avancé

#### Vérifier les logs MySQL

**Windows :**
- Les logs MySQL sont généralement dans :
  - `C:\ProgramData\MySQL\MySQL Server 8.0\Data\`
  - Ou dans le répertoire d'installation MySQL

#### Vérifier le fichier `my.ini` ou `my.cnf`

Assurez-vous que MySQL est configuré pour accepter les connexions :
```ini
[mysqld]
bind-address = 127.0.0.1
port = 3306
```

### 📝 Checklist rapide

- [ ] MySQL est démarré (vérifier dans Services Windows)
- [ ] Le port 3306 est accessible
- [ ] Les identifiants sont corrects (root/password)
- [ ] La base de données `customer_db` existe (ou sera créée automatiquement)
- [ ] Le profil `local` est activé dans IntelliJ Run Configuration
- [ ] Les variables d'environnement `DB_USERNAME` et `DB_PASSWORD` sont définies (si utilisées)

### 🚀 Solution alternative : Utiliser H2 (base de données en mémoire)

Si vous voulez tester rapidement sans MySQL, vous pouvez utiliser H2 :

1. Ajoutez H2 dans `pom.xml` (déjà présent normalement)
2. Créez un profil `test` avec H2 :
   ```properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driver-class-name=org.h2.Driver
   spring.jpa.hibernate.ddl-auto=create-drop
   ```
3. Activez le profil `test` dans IntelliJ

### 💡 Astuce

Si vous utilisez **XAMPP** ou **WAMP** :
- Assurez-vous que le service MySQL est démarré dans le panneau de contrôle
- Vérifiez que le port 3306 n'est pas utilisé par un autre service

