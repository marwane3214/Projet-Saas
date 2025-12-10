# SoapUI Testing Guide - Complete Instructions

## Step 1: Create New SOAP Project in SoapUI

1. **Open SoapUI**
2. **File → New SOAP Project**
3. **Enter Project Name:** `Billing Service`
4. **Initial WSDL:** `http://localhost:8080/ws/invoices.wsdl`
5. **Click OK**

SoapUI will automatically create requests for all 4 operations!

---

## Step 2: Test Each Operation

### Operation 1: createInvoiceRequest

**Request Name:** `createInvoiceRequest`

**Fill in the form with these values:**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:bill="http://example.com/billing/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <bill:createInvoiceRequest>
         <bill:customerId>1</bill:customerId>
         <bill:subscriptionId>1</bill:subscriptionId>
         <bill:amount>100.50</bill:amount>
         <bill:currency>USD</bill:currency>
         <bill:dueDate>2024-12-31</bill:dueDate>
         <bill:notes>Test invoice from SoapUI</bill:notes>
      </bill:createInvoiceRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Or fill in the form fields:**
- `customerId`: `1`
- `subscriptionId`: `1`
- `amount`: `100.50`
- `currency`: `USD` (optional, defaults to USD)
- `dueDate`: `2024-12-31` (format: YYYY-MM-DD)
- `notes`: `Test invoice from SoapUI` (optional)
- `idempotencyKey`: (leave empty or use unique value)

**Click "Submit"** (green play button)

**Expected Response:**
```xml
<soap:Envelope>
   <soap:Body>
      <ns2:createInvoiceResponse>
         <ns2:invoice>
            <ns2:id>1</ns2:id>
            <ns2:number>INV-1234567890-ABC12345</ns2:number>
            <ns2:customerId>1</ns2:customerId>
            <ns2:subscriptionId>1</ns2:subscriptionId>
            <ns2:amount>100.50</ns2:amount>
            <ns2:currency>USD</ns2:currency>
            <ns2:status>OPEN</ns2:status>
            <ns2:dueDate>2024-12-31</ns2:dueDate>
         </ns2:invoice>
      </ns2:createInvoiceResponse>
   </soap:Body>
</soap:Envelope>
```

**Save the `id` from response** (e.g., `1`) for next tests!

---

### Operation 2: getInvoiceRequest

**Request Name:** `getInvoiceRequest`

**Fill in:**
- `id`: `1` (use the ID from createInvoiceRequest response)

**Click "Submit"**

**Expected Response:** Invoice details with the specified ID

---

### Operation 3: getInvoicesByCustomerRequest

**Request Name:** `getInvoicesByCustomerRequest`

**Fill in:**
- `customerId`: `1`

**Click "Submit"**

**Expected Response:** List of all invoices for customer ID 1

---

### Operation 4: markInvoiceAsPaidRequest

**Request Name:** `markInvoiceAsPaidRequest`

**Fill in:**
- `id`: `1` (use invoice ID from createInvoiceRequest)

**Click "Submit"**

**Expected Response:** Invoice with status changed to "PAID"

---

## Complete Test Sequence

### Test 1: Create Invoice
1. Open `createInvoiceRequest`
2. Fill in:
   - customerId: `1`
   - subscriptionId: `1`
   - amount: `100.50`
   - currency: `USD`
   - dueDate: `2024-12-31`
3. Submit
4. **Save the invoice ID from response** (e.g., `id: 1`)

### Test 2: Get Invoice
1. Open `getInvoiceRequest`
2. Fill in:
   - id: `1` (from Test 1)
3. Submit
4. Verify invoice details are returned

### Test 3: Get Invoices by Customer
1. Open `getInvoicesByCustomerRequest`
2. Fill in:
   - customerId: `1`
3. Submit
4. Verify list contains the invoice from Test 1

### Test 4: Mark as Paid
1. Open `markInvoiceAsPaidRequest`
2. Fill in:
   - id: `1` (from Test 1)
3. Submit
4. Verify status changed to "PAID"

---

## SoapUI Form Fields Reference

### createInvoiceRequest Form:
```
┌─────────────────────┬──────────────────┐
│ Field               │ Example Value    │
├─────────────────────┼──────────────────┤
│ customerId          │ 1                │
│ subscriptionId      │ 1                │
│ amount              │ 100.50           │
│ currency            │ USD              │
│ dueDate             │ 2024-12-31       │
│ notes               │ Test invoice     │
│ idempotencyKey      │ (optional)       │
└─────────────────────┴──────────────────┘
```

### getInvoiceRequest Form:
```
┌─────────────────────┬──────────────────┐
│ Field               │ Example Value    │
├─────────────────────┼──────────────────┤
│ id                  │ 1                │
└─────────────────────┴──────────────────┘
```

### getInvoicesByCustomerRequest Form:
```
┌─────────────────────┬──────────────────┐
│ Field               │ Example Value    │
├─────────────────────┼──────────────────┤
│ customerId          │ 1                │
└─────────────────────┴──────────────────┘
```

### markInvoiceAsPaidRequest Form:
```
┌─────────────────────┬──────────────────┐
│ Field               │ Example Value    │
├─────────────────────┼──────────────────┤
│ id                  │ 1                │
└─────────────────────┴──────────────────┘
```

---

## Troubleshooting

**WSDL not loading:**
- Make sure application is running
- Check: http://localhost:8080/ws/invoices.wsdl in browser
- Verify port 8080 is correct

**Request fails:**
- Check application logs for errors
- Verify MySQL is running
- Verify database `billingdb` exists
- Check date format: YYYY-MM-DD

**Empty response:**
- Check if invoice was created (use getInvoiceRequest)
- Verify database connection

---

## Quick Copy-Paste Values

### Create Invoice:
```
customerId: 1
subscriptionId: 1
amount: 100.50
currency: USD
dueDate: 2024-12-31
notes: Test from SoapUI
```

### Get Invoice:
```
id: 1
```

### Get Invoices by Customer:
```
customerId: 1
```

### Mark as Paid:
```
id: 1
```

---

**That's it! Just fill in the form fields in SoapUI and click Submit!** 🚀

