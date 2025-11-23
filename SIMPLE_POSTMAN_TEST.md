# Simple Postman Testing Guide

## Base URL
```
http://localhost:8080
```

---

## 1. CREATE INVOICE

**Method:** `POST`  
**URL:** `http://localhost:8080/invoices`  
**Headers:** 
- Key: `Content-Type`
- Value: `application/json`

**Body (raw JSON):**
```json
{
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "dueDate": "2024-12-31",
  "notes": "Test invoice"
}
```

**Expected:** Status `201 Created`

---

## 2. GET INVOICE BY ID

**Method:** `GET`  
**URL:** `http://localhost:8080/invoices/1`

**Expected:** Status `200 OK` (replace `1` with actual invoice ID from step 1)

---

## 3. GET ALL INVOICES

**Method:** `GET`  
**URL:** `http://localhost:8080/invoices`

**Expected:** Status `200 OK` - Returns array of all invoices

---

## 4. GET INVOICES BY CUSTOMER

**Method:** `GET`  
**URL:** `http://localhost:8080/invoices/customer/1`

**Expected:** Status `200 OK` - Returns invoices for customer ID 1

---

## 5. MARK INVOICE AS PAID

**Method:** `POST`  
**URL:** `http://localhost:8080/invoices/1/pay`

**Expected:** Status `200 OK` (replace `1` with actual invoice ID)

---

## 6. REGISTER PAYMENT

**Method:** `POST`  
**URL:** `http://localhost:8080/payments`  
**Headers:** 
- Key: `Content-Type`
- Value: `application/json`

**Body (raw JSON):**
```json
{
  "invoiceId": 1,
  "amount": 100.50,
  "method": "CREDIT_CARD",
  "gatewayReference": "pay_123456"
}
```

**Expected:** Status `201 Created` (replace `invoiceId` with actual ID from step 1)

---

## 7. GET PAYMENT BY ID

**Method:** `GET`  
**URL:** `http://localhost:8080/payments/1`

**Expected:** Status `200 OK` (replace `1` with actual payment ID from step 6)

---

## 8. GET PAYMENTS BY INVOICE

**Method:** `GET`  
**URL:** `http://localhost:8080/payments/invoice/1`

**Expected:** Status `200 OK` (replace `1` with actual invoice ID)

---

## 9. SCHEDULE RENEWAL

**Method:** `POST`  
**URL:** `http://localhost:8080/renewals/schedule`  
**Headers:** 
- Key: `Content-Type`
- Value: `application/json`

**Body (raw JSON):**
```json
{
  "subscriptionId": 1,
  "renewalDate": "2024-12-31"
}
```

**Expected:** Status `201 Created`

---

## 10. PROCESS RENEWAL

**Method:** `POST`  
**URL:** `http://localhost:8080/renewals/process/1`

**Expected:** Status `200 OK` (replace `1` with actual renewal ID from step 9)

---

## 11. GET RENEWAL BY ID

**Method:** `GET`  
**URL:** `http://localhost:8080/renewals/1`

**Expected:** Status `200 OK` (replace `1` with actual renewal ID)

---

## 12. ISSUE CREDIT

**Method:** `POST`  
**URL:** `http://localhost:8080/credits`  
**Headers:** 
- Key: `Content-Type`
- Value: `application/json`

**Body (raw JSON):**
```json
{
  "customerId": 1,
  "amount": 25.00,
  "reason": "Customer refund"
}
```

**Expected:** Status `201 Created`

---

## 13. APPLY CREDIT TO INVOICE

**Method:** `POST`  
**URL:** `http://localhost:8080/credits/1/apply/1`

**Expected:** Status `200 OK` 
- First `1` = credit ID (from step 12)
- Second `1` = invoice ID (from step 1)

---

## 14. GET CREDIT BY ID

**Method:** `GET`  
**URL:** `http://localhost:8080/credits/1`

**Expected:** Status `200 OK` (replace `1` with actual credit ID)

---

## 15. HEALTH CHECK

**Method:** `GET`  
**URL:** `http://localhost:8080/actuator/health`

**Expected:** Status `200 OK` - Returns `{"status":"UP"}`

---

## Quick Test Sequence (Copy-Paste Ready)

### Step 1: Health Check
```
GET http://localhost:8080/actuator/health
```

### Step 2: Create Invoice
```
POST http://localhost:8080/invoices
Content-Type: application/json

{
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "dueDate": "2024-12-31"
}
```
**Save the `id` from response** (e.g., `"id": 1`)

### Step 3: Get Invoice
```
GET http://localhost:8080/invoices/1
```
(Use the ID from step 2)

### Step 4: Register Payment
```
POST http://localhost:8080/payments
Content-Type: application/json

{
  "invoiceId": 1,
  "amount": 100.50,
  "method": "CREDIT_CARD",
  "gatewayReference": "pay_001"
}
```
(Use invoice ID from step 2)

### Step 5: Mark Invoice Paid
```
POST http://localhost:8080/invoices/1/pay
```
(Use invoice ID from step 2)

---

## Postman Setup Tips

1. **Create Environment Variable:**
   - Click "Environments" → "Create Environment"
   - Add variable: `base_url` = `http://localhost:8080`
   - Use: `{{base_url}}/invoices` in requests

2. **Save Responses:**
   - After each POST, save the `id` from response
   - Use it in subsequent GET/POST requests

3. **Collection:**
   - Import `Billing-Service.postman_collection.json` for all requests pre-configured

---

## Common Values to Replace

- `1` in URLs = Use actual IDs from previous responses
- `customerId: 1` = Can use any number
- `subscriptionId: 1` = Can use any number
- `amount: 100.50` = Any positive number
- `dueDate: "2024-12-31"` = Format: YYYY-MM-DD

---

## All Endpoints Summary

| Method | URL | Body Required? |
|--------|-----|----------------|
| POST | `/invoices` | ✅ Yes |
| GET | `/invoices/{id}` | ❌ No |
| GET | `/invoices` | ❌ No |
| GET | `/invoices/customer/{customerId}` | ❌ No |
| POST | `/invoices/{id}/pay` | ❌ No |
| POST | `/invoices/{id}/reconcile` | ❌ No |
| POST | `/payments` | ✅ Yes |
| GET | `/payments/{id}` | ❌ No |
| GET | `/payments/invoice/{invoiceId}` | ❌ No |
| POST | `/renewals/schedule` | ✅ Yes |
| POST | `/renewals/process/{id}` | ❌ No |
| GET | `/renewals/{id}` | ❌ No |
| POST | `/credits` | ✅ Yes |
| POST | `/credits/{creditId}/apply/{invoiceId}` | ❌ No |
| GET | `/credits/{id}` | ❌ No |
| GET | `/credits/customer/{customerId}` | ❌ No |

---

That's it! Just copy-paste the URLs and JSON bodies above. 🚀

