# 🚀 Test SOAP API Now - Step by Step

## Quick Test (5 Minutes)

### 1. Start Application
```bash
run-soap.bat
```

Wait for: `Started BillingApplication` in console

### 2. Check WSDL (Browser)
Open: **http://localhost:8080/ws/invoices.wsdl**

✅ If you see XML → SOAP is working!

### 3. Test in Postman

**Copy this exact request:**

**URL:** `http://localhost:8080/ws`

**Method:** `POST`

**Headers:**
```
Content-Type: text/xml
SOAPAction: createInvoiceRequest
```

**Body (raw → XML):**
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

**Click Send**

### 4. Expected Result

You should get XML response like:
```xml
<ns2:createInvoiceResponse>
   <ns2:invoice>
      <ns2:id>1</ns2:id>
      <ns2:number>INV-...</ns2:number>
      <ns2:customerId>1</ns2:customerId>
      <ns2:amount>100.50</ns2:amount>
      <ns2:status>OPEN</ns2:status>
   </ns2:invoice>
</ns2:createInvoiceResponse>
```

---

## ✅ Success Indicators

- ✅ Application starts without errors
- ✅ WSDL URL returns XML
- ✅ SOAP request returns 200 OK
- ✅ Response contains invoice data
- ✅ Invoice is created in database

---

## 🔧 If Something Fails

1. **Check application logs** - Look for errors
2. **Verify MySQL is running** - XAMPP Control Panel
3. **Check port 8080** - Make sure nothing else is using it
4. **Verify database exists** - Run `check-database.bat`

---

**Ready to test!** Start the app and follow the steps above. 🎉

