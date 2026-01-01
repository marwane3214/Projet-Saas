# Commandes Git Rapides - Customer Service

## 🚀 Commandes à exécuter dans l'ordre

### 1. Depuis le dossier parent (micro_service)

```bash
# Cloner le dépôt (si pas déjà fait)
git clone https://github.com/marwane3214/Project-Saas.git

# Copier customer-service dans le dépôt
# Windows PowerShell:
Copy-Item -Path customer-service -Destination Project-Saas\customer-service -Recurse

# Windows CMD:
xcopy customer-service Project-Saas\customer-service /E /I

# Linux/Mac:
cp -r customer-service Project-Saas/
```

### 2. Dans le dépôt cloné

```bash
# Aller dans le dépôt
cd Project-Saas

# Vérifier la structure
ls

# Vérifier l'état Git
git status

# Ajouter customer-service
git add customer-service/

# Commiter
git commit -m "feat: add customer-service microservice

- Complete CRUD for Customer, Company, Contact, BillingAddress
- DDD/Hexagonal architecture
- MySQL support
- OpenAPI/Swagger documentation
- Unit and integration tests"

# Pousser vers GitHub
git push origin main
```

## 🔄 Workflow quotidien

```bash
# Récupérer les dernières modifications
git pull origin main

# Faire vos modifications...

# Voir ce qui a changé
git status
git diff

# Ajouter les modifications
git add .

# Commiter
git commit -m "Description de vos changements"

# Pousser
git push origin main
```

## 🌿 Créer une branche pour une nouvelle fonctionnalité

```bash
# Créer et basculer sur une nouvelle branche
git checkout -b feature/nom-de-la-fonctionnalite

# Faire vos modifications...

# Commiter
git add .
git commit -m "feat: description"

# Pousser la branche
git push origin feature/nom-de-la-fonctionnalite

# Créer une Pull Request sur GitHub
```

## 📦 Commandes utiles

```bash
# Voir l'historique
git log --oneline --graph

# Voir les différences
git diff

# Annuler des modifications non commitées
git checkout -- fichier

# Annuler un commit (garder les modifications)
git reset --soft HEAD~1

# Voir les branches
git branch -a

# Changer de branche
git checkout nom-de-la-branche
```

