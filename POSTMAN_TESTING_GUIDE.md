# Postman Testing Guide - Billing Service API

## Base URL
```
http://localhost:8080
```

## Quick Start

1. **Start the application**: `./mvnw spring-boot:run`
2. **Verify it's running**: http://localhost:8080/actuator/health
3. **View API Docs**: http://localhost:8080/swagger-ui.html

---

## 📋 Invoice Endpoints

### 1. Create Invoice
**POST** `/invoices`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "dueDate": "2024-12-31",
  "notes": "Monthly subscription invoice",
  "idempotencyKey": "inv-001-unique-key"
}
```

**Expected Response:** `201 Created`
```json
{
  "id": 1,
  "number": "INV-1234567890-ABC12345",
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "status": "OPEN",
  "dueDate": "2024-12-31",
  "issuedAt": "2024-11-23T14:00:00",
  "notes": "Monthly subscription invoice"
}
```

---

### 2. Get Invoice by ID
**GET** `/invoices/{id}`

**Example:** `GET /invoices/1`

**Expected Response:** `200 OK`
```json
{
  "id": 1,
  "number": "INV-1234567890-ABC12345",
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "status": "OPEN",
  "dueDate": "2024-12-31",
  "issuedAt": "2024-11-23T14:00:00",
  "notes": "Monthly subscription invoice"
}
```

---

### 3. Get Invoices by Customer
**GET** `/invoices/customer/{customerId}`

**Example:** `GET /invoices/customer/1`

**Expected Response:** `200 OK`
```json
[
  {
    "id": 1,
    "number": "INV-1234567890-ABC12345",
    "customerId": 1,
    "subscriptionId": 1,
    "amount": 100.50,
    "currency": "USD",
    "status": "OPEN",
    "dueDate": "2024-12-31",
    "issuedAt": "2024-11-23T14:00:00"
  }
]
```

---

### 4. Mark Invoice as Paid
**POST** `/invoices/{id}/pay`

**Example:** `POST /invoices/1/pay`

**Expected Response:** `200 OK`
```json
{
  "id": 1,
  "number": "INV-1234567890-ABC12345",
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "status": "PAID",
  "dueDate": "2024-12-31",
  "issuedAt": "2024-11-23T14:00:00"
}
```

---

### 5. Trigger Reconciliation
**POST** `/invoices/{id}/reconcile`

**Example:** `POST /invoices/1/reconcile`

**Expected Response:** `200 OK` (empty body)

---

### 6. Get All Invoices
**GET** `/invoices`

**Expected Response:** `200 OK`
```json
[
  {
    "id": 1,
    "number": "INV-1234567890-ABC12345",
    "customerId": 1,
    "subscriptionId": 1,
    "amount": 100.50,
    "currency": "USD",
    "status": "OPEN",
    "dueDate": "2024-12-31"
  }
]
```

---

## 💳 Payment Endpoints

### 1. Register Payment
**POST** `/payments`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "invoiceId": 1,
  "amount": 100.50,
  "method": "CREDIT_CARD",
  "gatewayReference": "pay_1234567890",
  "idempotencyKey": "pay-001-unique-key"
}
```

**Expected Response:** `201 Created`
```json
{
  "id": 1,
  "invoiceId": 1,
  "amount": 100.50,
  "method": "CREDIT_CARD",
  "gatewayReference": "pay_1234567890",
  "status": "SUCCESS",
  "createdAt": "2024-11-23T14:05:00"
}
```

---

### 2. Get Payment by ID
**GET** `/payments/{id}`

**Example:** `GET /payments/1`

**Expected Response:** `200 OK`
```json
{
  "id": 1,
  "invoiceId": 1,
  "amount": 100.50,
  "method": "CREDIT_CARD",
  "gatewayReference": "pay_1234567890",
  "status": "SUCCESS",
  "createdAt": "2024-11-23T14:05:00"
}
```

---

### 3. Get Payments by Invoice
**GET** `/payments/invoice/{invoiceId}`

**Example:** `GET /payments/invoice/1`

**Expected Response:** `200 OK`
```json
[
  {
    "id": 1,
    "invoiceId": 1,
    "amount": 100.50,
    "method": "CREDIT_CARD",
    "gatewayReference": "pay_1234567890",
    "status": "SUCCESS",
    "createdAt": "2024-11-23T14:05:00"
  }
]
```

---

## 🔄 Renewal Endpoints

### 1. Schedule Renewal
**POST** `/renewals/schedule`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "subscriptionId": 1,
  "renewalDate": "2024-12-31"
}
```

**Expected Response:** `201 Created`
```json
{
  "id": 1,
  "subscriptionId": 1,
  "renewalDate": "2024-12-31",
  "status": "SCHEDULED",
  "createdAt": "2024-11-23T14:06:00",
  "retryCount": 0
}
```

---

### 2. Process Renewal
**POST** `/renewals/process/{id}`

**Example:** `POST /renewals/process/1`

**Expected Response:** `200 OK`
```json
{
  "id": 1,
  "subscriptionId": 1,
  "renewalDate": "2024-12-31",
  "status": "PROCESSED",
  "createdAt": "2024-11-23T14:06:00",
  "processedAt": "2024-11-23T14:07:00",
  "retryCount": 0
}
```

---

### 3. Get Renewal by ID
**GET** `/renewals/{id}`

**Example:** `GET /renewals/1`

---

### 4. Get Renewals by Subscription
**GET** `/renewals/subscription/{subscriptionId}`

**Example:** `GET /renewals/subscription/1`

---

## 💰 Credit Endpoints

### 1. Issue Credit
**POST** `/credits`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "customerId": 1,
  "invoiceId": 1,
  "amount": 25.00,
  "reason": "Customer refund for service issue"
}
```

**Expected Response:** `201 Created`
```json
{
  "id": 1,
  "customerId": 1,
  "invoiceId": 1,
  "amount": 25.00,
  "reason": "Customer refund for service issue",
  "createdAt": "2024-11-23T14:08:00",
  "applied": false
}
```

---

### 2. Apply Credit to Invoice
**POST** `/credits/{creditId}/apply/{invoiceId}`

**Example:** `POST /credits/1/apply/1`

**Expected Response:** `200 OK`
```json
{
  "id": 1,
  "customerId": 1,
  "invoiceId": 1,
  "amount": 25.00,
  "reason": "Customer refund for service issue",
  "createdAt": "2024-11-23T14:08:00",
  "applied": true
}
```

---

### 3. Get Credit by ID
**GET** `/credits/{id}`

**Example:** `GET /credits/1`

---

### 4. Get Credits by Customer
**GET** `/credits/customer/{customerId}`

**Example:** `GET /credits/customer/1`

---

## 🧪 Testing Workflow Example

### Complete Billing Flow:

1. **Create Invoice**
   ```
   POST /invoices
   Body: { "customerId": 1, "subscriptionId": 1, "amount": 100.00, "currency": "USD", "dueDate": "2024-12-31" }
   ```
   Save the `id` from response (e.g., `invoiceId = 1`)

2. **Register Payment**
   ```
   POST /payments
   Body: { "invoiceId": 1, "amount": 100.00, "method": "CREDIT_CARD", "gatewayReference": "pay_001" }
   ```

3. **Verify Invoice is Paid**
   ```
   GET /invoices/1
   ```
   Check `status` is `PAID`

4. **Issue Credit (if needed)**
   ```
   POST /credits
   Body: { "customerId": 1, "amount": 10.00, "reason": "Discount" }
   ```

5. **Apply Credit to Invoice**
   ```
   POST /credits/1/apply/1
   ```

---

## 📝 Postman Collection Setup

### Import Collection (Optional)

1. Open Postman
2. Click **Import**
3. Create a new collection: **Billing Service API**
4. Add all endpoints above as requests

### Environment Variables (Recommended)

Create a Postman Environment with:
- `base_url`: `http://localhost:8080`
- `invoice_id`: (will be set after creating invoice)
- `payment_id`: (will be set after creating payment)
- `customer_id`: `1`

Then use: `{{base_url}}/invoices` in your requests

---

## 🔍 Health Check & API Docs

### Health Check
**GET** `http://localhost:8080/actuator/health`

**Expected Response:**
```json
{
  "status": "UP"
}
```

### Swagger UI
Open in browser: `http://localhost:8080/swagger-ui.html`

### API Docs (JSON)
**GET** `http://localhost:8080/api-docs`

---

## ⚠️ Common Errors

### 400 Bad Request
- Check JSON syntax
- Verify required fields are present
- Check date format: `YYYY-MM-DD`

### 404 Not Found
- Verify the ID exists
- Check the endpoint URL

### 500 Internal Server Error
- Check application logs
- Verify database is running
- Check MySQL connection

---

## 💡 Tips

1. **Use Variables**: Store IDs from responses in Postman variables
2. **Test Sequences**: Create a collection with the complete flow
3. **Date Format**: Always use `YYYY-MM-DD` for dates
4. **Idempotency**: Use unique `idempotencyKey` for retry-safe operations
5. **Status Codes**: 
   - `201` = Created
   - `200` = Success
   - `400` = Bad Request
   - `404` = Not Found
   - `500` = Server Error

---

## 🚀 Quick Test Sequence

1. **Health Check**: `GET /actuator/health`
2. **Create Invoice**: `POST /invoices` (save invoice ID)
3. **Get Invoice**: `GET /invoices/{id}`
4. **Register Payment**: `POST /payments` (use invoice ID)
5. **Mark Invoice Paid**: `POST /invoices/{id}/pay`
6. **Verify**: `GET /invoices/{id}` (status should be PAID)

Happy Testing! 🎉

