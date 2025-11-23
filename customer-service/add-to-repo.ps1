# Script pour ajouter customer-service au dépôt GitHub
# Exécutez depuis le dossier micro_service

Write-Host "🚀 Ajout de customer-service au dépôt GitHub" -ForegroundColor Green
Write-Host ""

$gitPath = "C:\Program Files\Git\bin\git.exe"
$repoName = "Project-Saas"
$repoPath = Join-Path (Get-Location) $repoName

# Vérifier si le dépôt existe déjà
if (Test-Path $repoPath) {
    Write-Host "✅ Le dépôt existe déjà: $repoPath" -ForegroundColor Green
    Set-Location $repoPath
    
    # Vérifier si c'est un dépôt Git
    $isGitRepo = Test-Path (Join-Path $repoPath ".git")
    if (-not $isGitRepo) {
        Write-Host "📦 Initialisation du dépôt Git..." -ForegroundColor Yellow
        & $gitPath init
        & $gitPath branch -M main
    }
} else {
    Write-Host "📦 Création du dépôt local..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $repoPath -Force | Out-Null
    Set-Location $repoPath
    
    # Initialiser Git
    & $gitPath init
    & $gitPath branch -M main
    Write-Host "✅ Dépôt initialisé" -ForegroundColor Green
}

# Copier customer-service dans le dépôt
$customerServiceSource = Join-Path (Get-Location).Parent "customer-service"
$customerServiceDest = Join-Path $repoPath "customer-service"

if (Test-Path $customerServiceDest) {
    Write-Host "⚠️  customer-service existe déjà dans le dépôt" -ForegroundColor Yellow
    $overwrite = Read-Host "Voulez-vous le remplacer? (O/N)"
    if ($overwrite -eq "O" -or $overwrite -eq "o") {
        Remove-Item -Path $customerServiceDest -Recurse -Force
        Write-Host "🗑️  Ancien dossier supprimé" -ForegroundColor Yellow
    } else {
        Write-Host "❌ Opération annulée" -ForegroundColor Red
        exit 0
    }
}

Write-Host "📋 Copie de customer-service..." -ForegroundColor Yellow
Copy-Item -Path $customerServiceSource -Destination $customerServiceDest -Recurse -Force
Write-Host "✅ customer-service copié" -ForegroundColor Green

# Vérifier le remote
Write-Host ""
Write-Host "🔍 Vérification du remote GitHub..." -ForegroundColor Yellow
$remoteUrl = & $gitPath remote get-url origin 2>$null

if ($remoteUrl) {
    Write-Host "✅ Remote configuré: $remoteUrl" -ForegroundColor Green
} else {
    Write-Host "📝 Configuration du remote..." -ForegroundColor Yellow
    & $gitPath remote add origin https://github.com/marwane3214/Project-Saas.git
    Write-Host "✅ Remote ajouté" -ForegroundColor Green
}

# Vérifier l'état
Write-Host ""
Write-Host "📊 État Git:" -ForegroundColor Cyan
& $gitPath status

# Ajouter customer-service
Write-Host ""
Write-Host "➕ Ajout de customer-service au staging..." -ForegroundColor Yellow
& $gitPath add customer-service/

# Vérifier ce qui sera commité
Write-Host ""
Write-Host "📋 Fichiers à commiter:" -ForegroundColor Cyan
& $gitPath status --short

# Commiter
Write-Host ""
Write-Host "💾 Commit..." -ForegroundColor Yellow
$commitMessage = @"
feat: add customer-service microservice

- Complete CRUD for Customer, Company, Contact, BillingAddress
- DDD/Hexagonal architecture
- MySQL support
- OpenAPI/Swagger documentation
- Unit and integration tests
- RESTful API with proper error handling
"@

& $gitPath commit -m $commitMessage

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Commit réussi" -ForegroundColor Green
    
    # Demander si on veut pousser
    Write-Host ""
    $pushConfirm = Read-Host "Voulez-vous pousser vers GitHub maintenant? (O/N)"
    
    if ($pushConfirm -eq "O" -or $pushConfirm -eq "o") {
        Write-Host ""
        Write-Host "🚀 Push vers GitHub..." -ForegroundColor Yellow
        
        # Essayer de pousser
        & $gitPath push -u origin main 2>&1 | Tee-Object -Variable pushOutput
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "🎉 Succès! customer-service a été ajouté au dépôt GitHub" -ForegroundColor Green
            Write-Host "🔗 Vérifiez sur: https://github.com/marwane3214/Project-Saas" -ForegroundColor Cyan
        } else {
            Write-Host ""
            Write-Host "⚠️  Erreur lors du push. Causes possibles:" -ForegroundColor Yellow
            Write-Host "   1. Le dépôt n'existe pas encore sur GitHub" -ForegroundColor White
            Write-Host "   2. Authentification requise (token ou SSH)" -ForegroundColor White
            Write-Host "   3. Pas de permissions d'écriture" -ForegroundColor White
            Write-Host ""
            Write-Host "💡 Solutions:" -ForegroundColor Cyan
            Write-Host "   - Créez le dépôt sur GitHub d'abord" -ForegroundColor White
            Write-Host "   - Configurez l'authentification (voir GIT_TROUBLESHOOTING.md)" -ForegroundColor White
            Write-Host "   - Ou poussez manuellement plus tard avec: git push -u origin main" -ForegroundColor White
            Write-Host ""
            Write-Host "✅ Le commit local a été créé avec succès" -ForegroundColor Green
        }
    } else {
        Write-Host ""
        Write-Host "ℹ️  Commit créé localement" -ForegroundColor Yellow
        Write-Host "💡 Pour pousser plus tard:" -ForegroundColor Cyan
        Write-Host "   cd $repoPath" -ForegroundColor White
        Write-Host "   git push -u origin main" -ForegroundColor White
    }
} else {
    Write-Host "❌ Erreur lors du commit" -ForegroundColor Red
}

Write-Host ""
Write-Host "✅ Configuration terminée!" -ForegroundColor Green

