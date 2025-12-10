# 🚀 How to Run the Application

## Method 1: Using Batch File (Easiest)

```bash
run-soap.bat
```

This will:
- Set Java 17
- Start MySQL check
- Run the application

---

## Method 2: Manual Command

```bash
# Set Java 17
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

# Run application
.\mvnw.cmd spring-boot:run
```

---

## Method 3: Using PowerShell

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
.\mvnw.cmd spring-boot:run
```

---

## Verification

After starting, wait 20-30 seconds, then check:

1. **Health Check:**
   ```
   http://localhost:8080/actuator/health
   ```
   Should return: `{"status":"UP"}`

2. **WSDL (SOAP):**
   ```
   http://localhost:8080/ws/invoices.wsdl
   ```
   Should return XML

3. **Swagger (REST):**
   ```
   http://localhost:8080/swagger-ui.html
   ```
   Should show API documentation

---

## If Application Doesn't Start

1. **Check MySQL is running** in XAMPP Control Panel
2. **Verify database exists:** Run `check-database.bat`
3. **Check for errors** in console output
4. **Verify port 8080 is free:**
   ```bash
   netstat -ano | findstr :8080
   ```

---

## Expected Console Output

You should see:
```
Started BillingApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

---

**Start the app and test the SOAP API!** 🎉

