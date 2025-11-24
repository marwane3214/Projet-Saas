# 🚀 Guide de démarrage MySQL

## ❌ Problème actuel
**"Connection refused: connect"** - MySQL n'est pas accessible sur `localhost:3306`

## ✅ Solutions

### Option 1 : Démarrer MySQL via XAMPP/WAMP (si installé)

#### XAMPP
1. Ouvrez **XAMPP Control Panel**
2. Cliquez sur **Start** à côté de **MySQL**
3. Attendez que le statut passe à **Running** (fond vert)

#### WAMP
1. Ouvrez **WAMP Server**
2. Cliquez sur l'icône WAMP dans la barre des tâches
3. Allez dans **MySQL** → **Service** → **Start/Resume Service**

### Option 2 : Démarrer MySQL via Services Windows

1. Appuyez sur `Win + R`
2. Tapez `services.msc` et appuyez sur Entrée
3. Cherchez **MySQL** ou **MySQL80** dans la liste
4. Si l'état est **Arrêté**, cliquez droit → **Démarrer**

### Option 3 : Démarrer MySQL via ligne de commande

```powershell
# Si MySQL est installé comme service
net start MySQL80
# ou
net start MySQL

# Si MySQL est dans le PATH
mysqld --console
```

### Option 4 : Vérifier l'installation MySQL

Si MySQL n'est pas installé, vous avez plusieurs options :

#### A. Installer MySQL Server
1. Téléchargez depuis : https://dev.mysql.com/downloads/mysql/
2. Installez MySQL Server
3. Notez le mot de passe root que vous configurez
4. Démarrez le service MySQL

#### B. Utiliser XAMPP (recommandé pour développement)
1. Téléchargez XAMPP : https://www.apachefriends.org/
2. Installez XAMPP
3. Démarrez MySQL depuis le XAMPP Control Panel

#### C. Utiliser Docker (si Docker est installé)
```powershell
docker run --name mysql-customer -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=customer_db -p 3306:3306 -d mysql:8.0
```

## 🔧 Configuration IntelliJ Database Tool

Une fois MySQL démarré :

1. Ouvrez **View** → **Tool Windows** → **Database**
2. Cliquez sur votre connexion **@localhost**
3. Cliquez sur **Test Connection**
4. Si ça échoue :
   - Vérifiez que l'URL est : `jdbc:mysql://localhost:3306`
   - Vérifiez le **Username** : `root`
   - Vérifiez le **Password** (cliquez sur **Test Connection** pour entrer le mot de passe)
   - Vérifiez que le **Driver** est : `MySQL Connector/J`

## 🧪 Tester la connexion

### Via ligne de commande MySQL
```bash
mysql -u root -p
# Entrez votre mot de passe
```

### Via PowerShell (si MySQL est dans le PATH)
```powershell
mysql -u root -p
```

## 📝 Vérifier que MySQL écoute sur le port 3306

```powershell
netstat -an | findstr :3306
```

Vous devriez voir quelque chose comme :
```
TCP    0.0.0.0:3306           0.0.0.0:0              LISTENING
```

## ⚙️ Configuration de l'application

Une fois MySQL démarré, vérifiez dans `application-local.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/customer_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
```

**Note :** Si vous utilisez XAMPP, le mot de passe root est généralement **vide** (laissez vide).

## 🎯 Checklist rapide

- [ ] MySQL est installé (XAMPP, WAMP, ou MySQL Server)
- [ ] MySQL est démarré (vérifier dans XAMPP/WAMP ou Services)
- [ ] Le port 3306 est accessible (`netstat -an | findstr :3306`)
- [ ] La connexion IntelliJ Database Tool fonctionne
- [ ] Le mot de passe root est correct dans `application-local.properties`
- [ ] Le profil `local` est activé dans IntelliJ Run Configuration

## 💡 Astuce

Si vous utilisez **XAMPP** et que MySQL ne démarre pas :
- Vérifiez qu'aucun autre service n'utilise le port 3306
- Redémarrez XAMPP en tant qu'administrateur
- Vérifiez les logs dans `C:\xampp\mysql\data\mysql_error.log`

