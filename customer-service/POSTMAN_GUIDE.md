# Guide Postman - Customer Service API

## 🚀 Configuration de base

### URL de base
```
http://localhost:8080
```

### Headers communs
Pour toutes les requêtes, ajoutez :
```
Content-Type: application/json
Accept: application/json
```

---

## 📋 Ordre recommandé des tests

1. **Créer une Company** (nécessaire pour créer un Customer)
2. **Créer un Customer**
3. **Créer une BillingAddress**
4. **Créer un Contact**
5. **Tester les autres endpoints**

---

## 🏢 1. COMPANY Endpoints

### 1.1 Créer une Company

**Method:** `POST`  
**URL:** `http://localhost:8080/api/companies`

**Body (JSON):**
```json
{
  "legalName": "Acme Corporation",
  "vatNumber": "FR12345678901",
  "country": "France"
}
```

**Response attendue (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "legalName": "Acme Corporation",
  "vatNumber": "FR12345678901",
  "country": "France",
  "createdAt": "2025-11-23T13:00:00"
}
```

**💾 Sauvegardez l'`id` de la company pour les prochaines étapes !**

---

### 1.2 Obtenir toutes les Companies

**Method:** `GET`  
**URL:** `http://localhost:8080/api/companies`

**Response attendue (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "legalName": "Acme Corporation",
    "vatNumber": "FR12345678901",
    "country": "France",
    "createdAt": "2025-11-23T13:00:00"
  }
]
```

---

### 1.3 Obtenir une Company par ID

**Method:** `GET`  
**URL:** `http://localhost:8080/api/companies/{id}`

**Exemple:**
```
http://localhost:8080/api/companies/550e8400-e29b-41d4-a716-446655440000
```

---

### 1.4 Mettre à jour une Company

**Method:** `PUT`  
**URL:** `http://localhost:8080/api/companies/{id}`

**Body (JSON):**
```json
{
  "legalName": "Acme Corporation Updated",
  "vatNumber": "FR12345678901",
  "country": "France"
}
```

---

### 1.5 Supprimer une Company

**Method:** `DELETE`  
**URL:** `http://localhost:8080/api/companies/{id}`

**Response attendue (204 No Content)**

---

## 👤 2. CUSTOMER Endpoints

### 2.1 Créer un Customer

**Method:** `POST`  
**URL:** `http://localhost:8080/api/customers`

**Body (JSON):**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+33123456789",
  "companyId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**⚠️ Remplacez `companyId` par l'ID de la company créée précédemment !**

**Response attendue (201 Created):**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+33123456789",
  "companyId": "550e8400-e29b-41d4-a716-446655440000",
  "billingAddressId": null,
  "createdAt": "2025-11-23T13:00:00",
  "updatedAt": "2025-11-23T13:00:00"
}
```

**💾 Sauvegardez l'`id` du customer !**

---

### 2.2 Obtenir tous les Customers

**Method:** `GET`  
**URL:** `http://localhost:8080/api/customers`

---

### 2.3 Obtenir un Customer par ID

**Method:** `GET`  
**URL:** `http://localhost:8080/api/customers/{id}`

---

### 2.4 Mettre à jour un Customer

**Method:** `PUT`  
**URL:** `http://localhost:8080/api/customers/{id}`

**Body (JSON):**
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "+33987654321",
  "companyId": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

### 2.5 Supprimer un Customer

**Method:** `DELETE`  
**URL:** `http://localhost:8080/api/customers/{id}`

---

## 📍 3. BILLING ADDRESS Endpoints

### 3.1 Créer une BillingAddress

**Method:** `POST`  
**URL:** `http://localhost:8080/api/billing-addresses`

**Body (JSON):**
```json
{
  "line1": "123 Main Street",
  "line2": "Suite 100",
  "city": "Paris",
  "zipCode": "75001",
  "country": "France"
}
```

**Response attendue (201 Created):**
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440002",
  "line1": "123 Main Street",
  "line2": "Suite 100",
  "city": "Paris",
  "zipCode": "75001",
  "country": "France"
}
```

**💾 Sauvegardez l'`id` de la billing address !**

---

### 3.2 Attacher une BillingAddress à un Customer

**Method:** `POST`  
**URL:** `http://localhost:8080/api/billing-addresses/{billingAddressId}/attach/{customerId}`

**Exemple:**
```
http://localhost:8080/api/billing-addresses/770e8400-e29b-41d4-a716-446655440002/attach/660e8400-e29b-41d4-a716-446655440001
```

**Response attendue (200 OK)**

---

### 3.3 Détacher une BillingAddress d'un Customer

**Method:** `DELETE`  
**URL:** `http://localhost:8080/api/billing-addresses/{id}/detach`

---

### 3.4 Obtenir toutes les BillingAddresses

**Method:** `GET`  
**URL:** `http://localhost:8080/api/billing-addresses`

---

### 3.5 Obtenir une BillingAddress par ID

**Method:** `GET`  
**URL:** `http://localhost:8080/api/billing-addresses/{id}`

---

### 3.6 Mettre à jour une BillingAddress

**Method:** `PUT`  
**URL:** `http://localhost:8080/api/billing-addresses/{id}`

**Body (JSON):**
```json
{
  "line1": "456 New Street",
  "line2": "Apt 5",
  "city": "Lyon",
  "zipCode": "69001",
  "country": "France"
}
```

---

## 📞 4. CONTACT Endpoints

### 4.1 Créer un Contact

**Method:** `POST`  
**URL:** `http://localhost:8080/api/contacts`

**Body (JSON):**
```json
{
  "customerId": "660e8400-e29b-41d4-a716-446655440001",
  "type": "BILLING",
  "email": "contact.billing@example.com",
  "phone": "+33111222333"
}
```

**Types disponibles:** `BILLING`, `TECHNICAL`, `OWNER`

**Response attendue (201 Created):**
```json
{
  "id": "880e8400-e29b-41d4-a716-446655440003",
  "customerId": "660e8400-e29b-41d4-a716-446655440001",
  "type": "BILLING",
  "email": "contact.billing@example.com",
  "phone": "+33111222333"
}
```

---

### 4.2 Obtenir tous les Contacts

**Method:** `GET`  
**URL:** `http://localhost:8080/api/contacts`

---

### 4.3 Obtenir les Contacts d'un Customer

**Method:** `GET`  
**URL:** `http://localhost:8080/api/contacts/customers/{customerId}`

**Exemple:**
```
http://localhost:8080/api/contacts/customers/660e8400-e29b-41d4-a716-446655440001
```

**Response attendue (200 OK):**
```json
[
  {
    "id": "880e8400-e29b-41d4-a716-446655440003",
    "customerId": "660e8400-e29b-41d4-a716-446655440001",
    "type": "BILLING",
    "email": "contact.billing@example.com",
    "phone": "+33111222333"
  }
]
```

---

### 4.4 Obtenir un Contact par ID

**Method:** `GET`  
**URL:** `http://localhost:8080/api/contacts/{id}`

---

### 4.5 Mettre à jour un Contact

**Method:** `PUT`  
**URL:** `http://localhost:8080/api/contacts/{id}`

**Body (JSON):**
```json
{
  "customerId": "660e8400-e29b-41d4-a716-446655440001",
  "type": "TECHNICAL",
  "email": "contact.technical@example.com",
  "phone": "+33444555666"
}
```

---

### 4.6 Supprimer un Contact

**Method:** `DELETE`  
**URL:** `http://localhost:8080/api/contacts/{id}`

---

## 🔍 5. Documentation Swagger

### Accéder à Swagger UI

**URL:** `http://localhost:8080/swagger-ui.html`

Vous pouvez tester tous les endpoints directement depuis Swagger UI !

---

## ⚠️ 6. Gestion des erreurs

### Erreur 400 - Validation Failed

**Exemple avec email invalide:**
```json
{
  "timestamp": "2025-11-23T13:00:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Validation failed for one or more fields",
  "fieldErrors": [
    {
      "field": "email",
      "message": "Email must be valid",
      "rejectedValue": "invalid-email"
    }
  ]
}
```

### Erreur 404 - Resource Not Found

```json
{
  "timestamp": "2025-11-23T13:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with id: 00000000-0000-0000-0000-000000000000",
  "path": "/api/customers/00000000-0000-0000-0000-000000000000"
}
```

### Erreur 400 - Business Exception

**Exemple avec VAT number dupliqué:**
```json
{
  "timestamp": "2025-11-23T13:00:00",
  "status": 400,
  "error": "Business Error",
  "message": "Company with VAT number FR12345678901 already exists",
  "path": "/api/companies"
}
```

---

## 📦 7. Collection Postman

### Importer dans Postman

1. Créez une nouvelle collection "Customer Service"
2. Créez des dossiers pour chaque ressource :
   - Companies
   - Customers
   - Billing Addresses
   - Contacts

3. Créez une variable d'environnement :
   - Variable: `base_url`
   - Valeur: `http://localhost:8080`

4. Utilisez `{{base_url}}` dans vos URLs :
   ```
   {{base_url}}/api/companies
   ```

---

## 🧪 8. Scénario de test complet

### Workflow recommandé :

1. **POST** `/api/companies` → Créer Company → Sauvegarder `companyId`
2. **POST** `/api/customers` → Créer Customer avec `companyId` → Sauvegarder `customerId`
3. **POST** `/api/billing-addresses` → Créer BillingAddress → Sauvegarder `billingAddressId`
4. **POST** `/api/billing-addresses/{id}/attach/{customerId}` → Attacher l'adresse
5. **POST** `/api/contacts` → Créer Contact avec `customerId`
6. **GET** `/api/customers/{id}` → Vérifier le customer avec son adresse
7. **GET** `/api/contacts/customers/{customerId}` → Vérifier les contacts
8. **PUT** `/api/customers/{id}` → Mettre à jour le customer
9. **DELETE** `/api/contacts/{id}` → Supprimer un contact
10. **DELETE** `/api/customers/{id}` → Supprimer le customer

---

## 💡 Astuces Postman

1. **Tests automatiques** : Ajoutez des scripts de test dans l'onglet "Tests"
   ```javascript
   pm.test("Status code is 201", function () {
       pm.response.to.have.status(201);
   });
   
   pm.test("Response has id", function () {
       var jsonData = pm.response.json();
       pm.expect(jsonData).to.have.property('id');
   });
   ```

2. **Variables** : Utilisez des variables pour stocker les IDs
   ```javascript
   var jsonData = pm.response.json();
   pm.environment.set("companyId", jsonData.id);
   ```

3. **Pre-request Script** : Pour générer des données dynamiques
   ```javascript
   pm.variables.set("randomEmail", "test" + Math.random() + "@example.com");
   ```

---

## ✅ Checklist de test

- [ ] Créer une Company
- [ ] Lister toutes les Companies
- [ ] Obtenir une Company par ID
- [ ] Mettre à jour une Company
- [ ] Créer un Customer
- [ ] Lister tous les Customers
- [ ] Obtenir un Customer par ID
- [ ] Mettre à jour un Customer
- [ ] Créer une BillingAddress
- [ ] Attacher une BillingAddress à un Customer
- [ ] Créer un Contact
- [ ] Obtenir les Contacts d'un Customer
- [ ] Tester les validations (email invalide, phone invalide, etc.)
- [ ] Tester les erreurs 404
- [ ] Supprimer les ressources créées

---

Bon test ! 🚀

