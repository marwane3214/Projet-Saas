# Quick Start Guide - Build & Run Locally

## Prerequisites Check

```bash
# Check Java version (must be 17+)
java -version

# Check Maven (or use mvnw wrapper)
mvn --version
```

## Step 1: Setup Database

1. **Install XAMPP** (if not installed)
   - Download: https://www.apachefriends.org/
   - Includes MySQL

2. **Start MySQL**
   - Open XAMPP Control Panel
   - Click "Start" next to MySQL
   - Default port: 3306

3. **Create Database**
   ```sql
   -- Option 1: Using phpMyAdmin (http://localhost/phpmyadmin)
   -- Click "New" → Enter "billingdb" → Create
   
   -- Option 2: Using MySQL command line
   mysql -u root
   CREATE DATABASE billingdb;
   ```

4. **Update Credentials** (if needed)
   - Edit: `src/main/resources/application.properties`
   - Default XAMPP: username=`root`, password=empty

## Step 2: Build

**Windows:**
```cmd
build.bat
```

**Linux/Mac:**
```bash
./mvnw clean install
```

## Step 3: Run

**Windows:**
```cmd
run.bat
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

## Step 4: Verify

1. **Health Check**: http://localhost:8080/actuator/health
2. **Swagger UI**: http://localhost:8080/swagger-ui.html
3. **API Docs**: http://localhost:8080/api-docs

## Test the API

```bash
# Create an invoice
curl -X POST http://localhost:8080/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "subscriptionId": 1,
    "amount": 100.00,
    "currency": "USD",
    "dueDate": "2024-12-31"
  }'

# Get invoice
curl http://localhost:8080/invoices/1
```

## Troubleshooting

### "Connection refused" to MySQL
- Ensure MySQL is running in XAMPP Control Panel
- Check port 3306 is not blocked
- Verify database `billingdb` exists
- Check username/password in `application.properties` (default: root with no password)

### "Kafka connection failed"
- This is OK! The service works without Kafka
- Events just won't be published
- To enable: Install and start Kafka on localhost:9092

### Port 8080 already in use
- Change port in `application.properties`: `server.port=8081`
- Or stop the process using port 8080

### Java version error
- Must use Java 17+
- Check: `java -version`
- Set JAVA_HOME if needed

## Next Steps

- See [LOCAL_SETUP.md](LOCAL_SETUP.md) for detailed setup
- See [README.md](README.md) for full documentation
- Explore API at http://localhost:8080/swagger-ui.html

