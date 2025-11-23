# Script PowerShell pour connecter customer-service au dépôt GitHub
# Exécutez ce script depuis le dossier parent (micro_service)

Write-Host "🚀 Configuration Git pour Customer Service" -ForegroundColor Green
Write-Host ""

# Vérifier si Git est installé
try {
    $gitVersion = git --version
    Write-Host "✅ Git trouvé: $gitVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ Git n'est pas installé. Installez-le depuis: https://git-scm.com/downloads" -ForegroundColor Red
    exit 1
}

# Vérifier si nous sommes dans le bon dossier
$currentDir = Get-Location
Write-Host "📁 Dossier actuel: $currentDir" -ForegroundColor Yellow

# Demander confirmation
$confirm = Read-Host "Voulez-vous continuer? (O/N)"
if ($confirm -ne "O" -and $confirm -ne "o") {
    Write-Host "❌ Opération annulée" -ForegroundColor Red
    exit 0
}

# Étape 1: Cloner le dépôt (si pas déjà fait)
$repoPath = Join-Path $currentDir "Project-Saas"
if (-not (Test-Path $repoPath)) {
    Write-Host ""
    Write-Host "📥 Clonage du dépôt GitHub..." -ForegroundColor Yellow
    git clone https://github.com/marwane3214/Project-Saas.git
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Erreur lors du clonage" -ForegroundColor Red
        exit 1
    }
    Write-Host "✅ Dépôt cloné avec succès" -ForegroundColor Green
} else {
    Write-Host "✅ Le dépôt existe déjà: $repoPath" -ForegroundColor Green
}

# Étape 2: Copier customer-service dans le dépôt
$customerServicePath = Join-Path $currentDir "customer-service"
$destinationPath = Join-Path $repoPath "customer-service"

if (-not (Test-Path $customerServicePath)) {
    Write-Host "❌ Le dossier customer-service n'existe pas dans: $currentDir" -ForegroundColor Red
    exit 1
}

if (Test-Path $destinationPath) {
    $overwrite = Read-Host "⚠️  customer-service existe déjà dans le dépôt. Voulez-vous le remplacer? (O/N)"
    if ($overwrite -eq "O" -or $overwrite -eq "o") {
        Remove-Item -Path $destinationPath -Recurse -Force
        Write-Host "🗑️  Ancien dossier supprimé" -ForegroundColor Yellow
    } else {
        Write-Host "❌ Opération annulée" -ForegroundColor Red
        exit 0
    }
}

Write-Host ""
Write-Host "📋 Copie de customer-service dans le dépôt..." -ForegroundColor Yellow
Copy-Item -Path $customerServicePath -Destination $destinationPath -Recurse -Force
Write-Host "✅ customer-service copié avec succès" -ForegroundColor Green

# Étape 3: Ajouter au dépôt Git
Write-Host ""
Write-Host "📝 Configuration Git..." -ForegroundColor Yellow
Set-Location $repoPath

# Vérifier l'état
Write-Host ""
Write-Host "📊 État Git actuel:" -ForegroundColor Cyan
git status

# Ajouter customer-service
Write-Host ""
Write-Host "➕ Ajout de customer-service au dépôt..." -ForegroundColor Yellow
git add customer-service/

# Vérifier ce qui sera commité
Write-Host ""
Write-Host "📋 Fichiers à commiter:" -ForegroundColor Cyan
git status

# Demander confirmation pour commit
Write-Host ""
$commitConfirm = Read-Host "Voulez-vous commiter et pousser maintenant? (O/N)"
if ($commitConfirm -eq "O" -or $commitConfirm -eq "o") {
    # Commiter
    Write-Host ""
    Write-Host "💾 Commit..." -ForegroundColor Yellow
    git commit -m "feat: add customer-service microservice

- Complete CRUD for Customer, Company, Contact, BillingAddress
- DDD/Hexagonal architecture
- MySQL support
- OpenAPI/Swagger documentation
- Unit and integration tests"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Commit réussi" -ForegroundColor Green
        
        # Pousser
        Write-Host ""
        Write-Host "🚀 Push vers GitHub..." -ForegroundColor Yellow
        git push origin main
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "🎉 Succès! customer-service a été ajouté au dépôt GitHub" -ForegroundColor Green
            Write-Host "🔗 Vérifiez sur: https://github.com/marwane3214/Project-Saas" -ForegroundColor Cyan
        } else {
            Write-Host "❌ Erreur lors du push. Vérifiez vos credentials Git." -ForegroundColor Red
        }
    } else {
        Write-Host "❌ Erreur lors du commit" -ForegroundColor Red
    }
} else {
    Write-Host ""
    Write-Host "ℹ️  customer-service a été ajouté au staging area" -ForegroundColor Yellow
    Write-Host "💡 Exécutez manuellement:" -ForegroundColor Cyan
    Write-Host "   git commit -m 'feat: add customer-service microservice'" -ForegroundColor White
    Write-Host "   git push origin main" -ForegroundColor White
}

Write-Host ""
Write-Host "✅ Configuration terminée!" -ForegroundColor Green

