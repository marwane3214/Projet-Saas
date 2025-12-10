# 🚀 Run the Application - Simple Steps

## Quick Start

### Option 1: Double-Click Batch File
1. Double-click `start-app.bat`
2. Wait 20-30 seconds
3. Check: http://localhost:8080/actuator/health

### Option 2: Command Line

**Open Command Prompt or PowerShell in the project folder, then:**

```bash
# Set Java 17
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

# Run
.\mvnw.cmd spring-boot:run
```

**Or use the wrapper directly:**
```bash
.\mvnw.cmd spring-boot:run
```

---

## What to Look For

When the app starts successfully, you'll see:
```
Started BillingApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

---

## Verify It's Running

1. **Open browser:** http://localhost:8080/actuator/health
   - Should show: `{"status":"UP"}`

2. **Check WSDL:** http://localhost:8080/ws/invoices.wsdl
   - Should show XML

---

## If It Doesn't Start

1. **Check MySQL** - Make sure it's running in XAMPP
2. **Check database** - Run `check-database.bat`
3. **Check console** - Look for error messages
4. **Check Java** - Run `java -version` (should show 17)

---

## Test SOAP API

Once running, test in Postman:
- **URL:** `http://localhost:8080/ws`
- **Method:** `POST`
- **Headers:** `Content-Type: text/xml`, `SOAPAction: createInvoiceRequest`
- **Body:** Use XML from `test-soap.xml` or `SOAP_TESTING_GUIDE.md`

---

**Run `start-app.bat` or use the command above to start the application!** 🎯

