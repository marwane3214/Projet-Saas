# Résolution des problèmes Git - Customer Service

## ❌ Erreur : "Repository not found"

### Solutions possibles :

### 1. Vérifier l'URL du dépôt

Assurez-vous que l'URL est correcte :
- **HTTPS** : `https://github.com/marwane3214/Project-Saas.git`
- **SSH** : `git@github.com:marwane3214/Project-Saas.git`

### 2. Authentification requise

Si le dépôt est privé, vous devez vous authentifier :

#### Option A : Utiliser GitHub Desktop
1. Ouvrez GitHub Desktop
2. File → Clone Repository
3. Entrez l'URL : `https://github.com/marwane3214/Project-Saas`
4. Choisissez un dossier local
5. Clonez

#### Option B : Personal Access Token (HTTPS)

1. Créez un token GitHub :
   - Allez sur : https://github.com/settings/tokens
   - Cliquez sur "Generate new token (classic)"
   - Sélectionnez les permissions : `repo` (accès complet aux dépôts)
   - Copiez le token

2. Utilisez le token comme mot de passe :
   ```powershell
   git clone https://github.com/marwane3214/Project-Saas.git
   # Username: votre-username
   # Password: votre-token
   ```

#### Option C : Configuration SSH

```powershell
# 1. Générez une clé SSH
ssh-keygen -t ed25519 -C "votre.email@example.com"

# 2. Affichez la clé publique
cat ~/.ssh/id_ed25519.pub

# 3. Ajoutez-la sur GitHub : https://github.com/settings/keys

# 4. Clonez avec SSH
git clone git@github.com:marwane3214/Project-Saas.git
```

### 3. Vérifier les permissions

Assurez-vous que :
- Vous avez été invité au dépôt
- Vous avez accepté l'invitation : https://github.com/marwane3214/Project-Saas/invitations
- Vous avez les droits d'écriture

### 4. Alternative : Initialiser un nouveau dépôt

Si le dépôt n'existe pas encore, créez-le d'abord sur GitHub, puis :

```powershell
# Créer un nouveau dossier
mkdir Project-Saas
cd Project-Saas

# Initialiser Git
git init

# Ajouter le remote
git remote add origin https://github.com/marwane3214/Project-Saas.git

# Créer un README initial
echo "# Project-Saas" > README.md
git add README.md
git commit -m "Initial commit"
git branch -M main
git push -u origin main
```

## 🚀 Commandes après authentification réussie

Une fois que vous pouvez cloner le dépôt :

```powershell
# 1. Cloner le dépôt
git clone https://github.com/marwane3214/Project-Saas.git
cd Project-Saas

# 2. Copier customer-service
Copy-Item -Path ..\customer-service -Destination .\customer-service -Recurse

# 3. Ajouter au dépôt
git add customer-service/
git commit -m "feat: add customer-service microservice"
git push origin main
```

## 📝 Vérifier la configuration Git

```powershell
# Vérifier votre identité
git config --global user.name
git config --global user.email

# Configurer si nécessaire
git config --global user.name "Votre Nom"
git config --global user.email "votre.email@example.com"
```

## 🔍 Vérifier les remotes

```powershell
git remote -v
# Devrait afficher :
# origin  https://github.com/marwane3214/Project-Saas.git (fetch)
# origin  https://github.com/marwane3214/Project-Saas.git (push)
```

