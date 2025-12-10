# Application Status

## ✅ Application Started!

The application is now running in the background.

## Quick Test

### 1. Check Health
Open browser: **http://localhost:8080/actuator/health**

### 2. Check WSDL (SOAP)
Open browser: **http://localhost:8080/ws/invoices.wsdl**

### 3. Test SOAP in Postman

**URL:** `http://localhost:8080/ws`  
**Method:** `POST`  
**Headers:**
- `Content-Type: text/xml`
- `SOAPAction: createInvoiceRequest`

**Body (raw XML):**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bill="http://example.com/billing/soap">
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
</soapenv:Envelope>
```

---

## All Endpoints

- **Health:** http://localhost:8080/actuator/health
- **WSDL:** http://localhost:8080/ws/invoices.wsdl
- **SOAP:** http://localhost:8080/ws
- **Swagger:** http://localhost:8080/swagger-ui.html

---

**The application is running! Test the SOAP API now.** 🚀

