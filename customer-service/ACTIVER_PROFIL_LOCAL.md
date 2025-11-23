# Comment activer le profil `local` (H2)

## 🎯 Dans IntelliJ IDEA

### Méthode 1 : Via la configuration de run

1. **Run → Edit Configurations...**
2. Sélectionnez votre configuration `CustomerServiceApplication`
3. Dans la section **"Active profiles"**, ajoutez : `local`
4. Cliquez sur **Apply** puis **OK**
5. Lancez l'application

### Méthode 2 : Via les variables d'environnement

1. **Run → Edit Configurations...**
2. Sélectionnez votre configuration
3. Dans **"Environment variables"**, ajoutez :
   - Variable : `SPRING_PROFILES_ACTIVE`
   - Valeur : `local`
4. Lancez l'application

### Méthode 3 : Via les Program arguments

1. **Run → Edit Configurations...**
2. Dans **"Program arguments"**, ajoutez :
   ```
   --spring.profiles.active=local
   ```

## 🚀 Via la ligne de commande (Maven)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## ✅ Vérification

Une fois le profil activé, vous devriez voir dans les logs :

```
The following profiles are active: local
```

Et l'application utilisera H2 au lieu de PostgreSQL.

## 🔍 Console H2

Une fois l'application démarrée avec le profil `local`, accédez à :

- **URL** : http://localhost:8080/h2-console
- **JDBC URL** : `jdbc:h2:mem:customer_db`
- **Username** : `sa`
- **Password** : (vide)

