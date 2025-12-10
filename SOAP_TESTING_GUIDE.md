# SOAP API Testing Guide

## WSDL Location
Once the application is running, access the WSDL at:
```
http://localhost:8080/ws/invoices.wsdl
```

## SOAP Endpoints

### Base URL
```
http://localhost:8080/ws
```

### Namespace
```
http://example.com/billing/soap
```

---

## 1. CREATE INVOICE

**SOAP Action:** `createInvoiceRequest`  
**Endpoint:** `http://localhost:8080/ws`

**SOAP Request:**
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
         <bill:notes>Test invoice</bill:notes>
      </bill:createInvoiceRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Expected Response:**
```xml
<SOAP-ENV:Envelope>
   <SOAP-ENV:Body>
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
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

## 2. GET INVOICE BY ID

**SOAP Action:** `getInvoiceRequest`  
**Endpoint:** `http://localhost:8080/ws`

**SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:bill="http://example.com/billing/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <bill:getInvoiceRequest>
         <bill:id>1</bill:id>
      </bill:getInvoiceRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

---

## 3. GET INVOICES BY CUSTOMER

**SOAP Action:** `getInvoicesByCustomerRequest`  
**Endpoint:** `http://localhost:8080/ws`

**SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:bill="http://example.com/billing/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <bill:getInvoicesByCustomerRequest>
         <bill:customerId>1</bill:customerId>
      </bill:getInvoicesByCustomerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

---

## 4. MARK INVOICE AS PAID

**SOAP Action:** `markInvoiceAsPaidRequest`  
**Endpoint:** `http://localhost:8080/ws`

**SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:bill="http://example.com/billing/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <bill:markInvoiceAsPaidRequest>
         <bill:id>1</bill:id>
      </bill:markInvoiceAsPaidRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

---

## Testing with Postman

### Step 1: Get WSDL
1. Start the application
2. Open: `http://localhost:8080/ws/invoices.wsdl`
3. Copy the WSDL content

### Step 2: Create SOAP Request in Postman

1. **Create New Request**
   - Method: `POST`
   - URL: `http://localhost:8080/ws`

2. **Add Headers:**
   ```
   Content-Type: text/xml
   SOAPAction: createInvoiceRequest
   ```

3. **Body (raw XML):**
   - Select "raw" → "XML"
   - Paste the SOAP request XML from above

4. **Send Request**

---

## Testing with SoapUI (Recommended for SOAP)

1. **Download SoapUI**: https://www.soapui.org/
2. **Create New SOAP Project**
   - WSDL URL: `http://localhost:8080/ws/invoices.wsdl`
3. **Test Operations:**
   - createInvoiceRequest
   - getInvoiceRequest
   - getInvoicesByCustomerRequest
   - markInvoiceAsPaidRequest

---

## Testing with cURL

### Create Invoice
```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -H "SOAPAction: createInvoiceRequest" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bill="http://example.com/billing/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <bill:createInvoiceRequest>
         <bill:customerId>1</bill:customerId>
         <bill:subscriptionId>1</bill:subscriptionId>
         <bill:amount>100.50</bill:amount>
         <bill:currency>USD</bill:currency>
         <bill:dueDate>2024-12-31</bill:dueDate>
      </bill:createInvoiceRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

---

## Quick Test Sequence

1. **Start Application**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Verify WSDL is Available**
   - Open: http://localhost:8080/ws/invoices.wsdl
   - Should see XML WSDL definition

3. **Test Create Invoice** (use Postman or SoapUI)
   - Use the SOAP request XML above

4. **Verify Response**
   - Check for invoice ID in response
   - Verify status is "OPEN"

---

## Troubleshooting

**WSDL not found:**
- Check application is running
- Verify port 8080 is correct
- Check logs for errors

**SOAP request fails:**
- Verify XML format is correct
- Check namespace matches
- Verify all required fields are present

**Connection refused:**
- Application not running
- Wrong port number

---

## Available SOAP Operations

1. ✅ `createInvoiceRequest` - Create new invoice
2. ✅ `getInvoiceRequest` - Get invoice by ID
3. ✅ `getInvoicesByCustomerRequest` - Get all invoices for customer
4. ✅ `markInvoiceAsPaidRequest` - Mark invoice as paid

All operations use namespace: `http://example.com/billing/soap`

