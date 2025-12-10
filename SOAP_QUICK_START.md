# SOAP API - Quick Start & Testing

## ✅ What's Been Added

1. **SOAP Dependencies** - Added to `pom.xml`
2. **SOAP Endpoint** - `InvoiceEndpoint.java` with 4 operations
3. **WSDL Configuration** - Auto-generated WSDL
4. **XSD Schema** - `invoices.xsd` for request/response validation

## 🚀 How to Run & Test

### Step 1: Start the Application
```bash
./mvnw spring-boot:run
```

Wait for: `Started BillingApplication`

### Step 2: Access WSDL
Open in browser:
```
http://localhost:8080/ws/invoices.wsdl
```

You should see the WSDL XML definition.

### Step 3: Test SOAP Endpoint

#### Option A: Using Postman

1. **Create New Request**
   - Method: `POST`
   - URL: `http://localhost:8080/ws`

2. **Headers:**
   ```
   Content-Type: text/xml
   SOAPAction: createInvoiceRequest
   ```

3. **Body (raw XML):**
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
      </bill:createInvoiceRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

4. **Send** - You should get a SOAP response with invoice details

#### Option B: Using SoapUI (Recommended)

1. Download SoapUI: https://www.soapui.org/
2. Create New SOAP Project
3. WSDL URL: `http://localhost:8080/ws/invoices.wsdl`
4. SoapUI will auto-generate requests for all operations
5. Fill in values and test!

## 📋 Available SOAP Operations

1. **createInvoiceRequest** - Create new invoice
2. **getInvoiceRequest** - Get invoice by ID  
3. **getInvoicesByCustomerRequest** - Get invoices for customer
4. **markInvoiceAsPaidRequest** - Mark invoice as paid

## 🔗 Endpoints

- **WSDL:** http://localhost:8080/ws/invoices.wsdl
- **SOAP Endpoint:** http://localhost:8080/ws
- **Namespace:** http://example.com/billing/soap

## ✅ Verification

After starting the app, verify:
1. ✅ WSDL accessible at `/ws/invoices.wsdl`
2. ✅ Health check: http://localhost:8080/actuator/health
3. ✅ SOAP requests work via Postman/SoapUI

## 📝 Test File

I've created `test-soap.xml` with a sample SOAP request you can use!

---

**The SOAP API is ready!** Just start the application and test it. 🎉

