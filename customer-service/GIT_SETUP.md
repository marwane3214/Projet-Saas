# Guide de Configuration Git - Customer Service

## 🎯 Objectif
Connecter le microservice `customer-service` au dépôt GitHub existant : https://github.com/marwane3214/Project-Saas

## 📋 Prérequis

1. **Git installé** sur votre machine
   - Téléchargez depuis : https://git-scm.com/downloads
   - Ou utilisez Git Bash (inclus avec GitHub Desktop)

2. **Accès au dépôt GitHub**
   - Vous devez avoir été invité au dépôt
   - Vérifiez vos invitations : https://github.com/marwane3214/Project-Saas/invitations

## 🚀 Étapes de Configuration

### Option 1 : Si le dépôt est déjà cloné localement

Si vous avez déjà cloné le dépôt ailleurs :

```bash
# 1. Naviguez vers le dépôt cloné
cd /chemin/vers/Project-Saas

# 2. Vérifiez la structure existante
ls

# 3. Copiez le dossier customer-service dans le dépôt
# (Depuis le dossier parent de customer-service)
cp -r customer-service /chemin/vers/Project-Saas/

# 4. Ajoutez et commitez
cd /chemin/vers/Project-Saas
git add customer-service/
git commit -m "feat: add customer-service microservice"
git push origin main
```

### Option 2 : Cloner le dépôt et ajouter customer-service

```bash
# 1. Clonez le dépôt (si pas déjà fait)
git clone https://github.com/marwane3214/Project-Saas.git
cd Project-Saas

# 2. Vérifiez la structure existante
ls
# Vous devriez voir les autres services (ex: billing-service, etc.)

# 3. Copiez customer-service dans ce dépôt
# Depuis le dossier parent où se trouve customer-service
cp -r customer-service /chemin/vers/Project-Saas/

# 4. Ajoutez customer-service au dépôt
git add customer-service/
git commit -m "feat: add customer-service microservice"
git push origin main
```

### Option 3 : Depuis le dossier customer-service actuel

```bash
# 1. Naviguez vers le dossier parent (micro_service)
cd C:\Users\Administrator\Desktop\micro_service

# 2. Clonez le dépôt GitHub (si pas déjà fait)
git clone https://github.com/marwane3214/Project-Saas.git

# 3. Copiez customer-service dans Project-Saas
# Windows PowerShell
Copy-Item -Path customer-service -Destination Project-Saas\customer-service -Recurse

# Ou Windows CMD
xcopy customer-service Project-Saas\customer-service /E /I

# 4. Naviguez dans le dépôt cloné
cd Project-Saas

# 5. Vérifiez que customer-service est bien là
ls customer-service

# 6. Ajoutez et commitez
git add customer-service/
git commit -m "feat: add customer-service microservice with full CRUD operations"
git push origin main
```

## 📁 Structure attendue du dépôt

Après l'ajout, la structure devrait ressembler à :

```
Project-Saas/
├── billing-service/
├── customer-service/     ← Votre nouveau service
│   ├── src/
│   ├── pom.xml
│   ├── README.md
│   └── ...
├── other-service/
├── README.md
└── .gitignore
```

## ⚙️ Configuration Git (si nécessaire)

### Configurer votre identité Git

```bash
git config --global user.name "Votre Nom"
git config --global user.email "votre.email@example.com"
```

### Vérifier les remotes

```bash
git remote -v
# Devrait afficher :
# origin  https://github.com/marwane3214/Project-Saas.git (fetch)
# origin  https://github.com/marwane3214/Project-Saas.git (push)
```

## 🔐 Authentification GitHub

### Option 1 : HTTPS avec Personal Access Token

1. Créez un token : https://github.com/settings/tokens
2. Utilisez le token comme mot de passe lors du push

### Option 2 : SSH (recommandé)

```bash
# Générez une clé SSH (si pas déjà fait)
ssh-keygen -t ed25519 -C "votre.email@example.com"

# Ajoutez la clé à votre compte GitHub
# Copiez le contenu de ~/.ssh/id_ed25519.pub
# Ajoutez-le ici : https://github.com/settings/keys

# Changez l'URL du remote en SSH
git remote set-url origin git@github.com:marwane3214/Project-Saas.git
```

## 📝 Commandes Git essentielles

```bash
# Voir l'état
git status

# Ajouter tous les fichiers
git add .

# Commiter
git commit -m "Description du changement"

# Pousser vers GitHub
git push origin main

# Récupérer les dernières modifications
git pull origin main

# Voir l'historique
git log --oneline
```

## 🚨 Résolution de problèmes

### Erreur : "remote origin already exists"

```bash
# Vérifiez les remotes
git remote -v

# Si nécessaire, changez l'URL
git remote set-url origin https://github.com/marwane3214/Project-Saas.git
```

### Erreur : "Permission denied"

- Vérifiez que vous avez été invité au dépôt
- Vérifiez vos credentials Git
- Utilisez un Personal Access Token si nécessaire

### Erreur : "Updates were rejected"

```bash
# Récupérez d'abord les modifications
git pull origin main --rebase

# Puis poussez à nouveau
git push origin main
```

## ✅ Checklist avant de pousser

- [ ] Le dossier `customer-service` est dans le dépôt cloné
- [ ] Tous les fichiers sont présents (src/, pom.xml, README.md, etc.)
- [ ] Le `.gitignore` est correct
- [ ] Les fichiers sensibles ne sont pas commités (credentials, etc.)
- [ ] Le commit message est descriptif
- [ ] Vous avez les droits d'écriture sur le dépôt

## 🎉 Après le push

1. Vérifiez sur GitHub : https://github.com/marwane3214/Project-Saas
2. Le dossier `customer-service` devrait apparaître
3. Vos collaborateurs peuvent maintenant le cloner

## 📚 Ressources

- Documentation Git : https://git-scm.com/doc
- GitHub Guides : https://guides.github.com/
- Git Cheat Sheet : https://education.github.com/git-cheat-sheet-education.pdf

