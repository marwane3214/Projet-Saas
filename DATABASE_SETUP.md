# Database Setup - Quick Fix

## The Error
```
Failed to configure a DataSource: 'url' attribute is not specified
```

## What I Fixed

1. ✅ **Removed conflicting `application.yml`** - Only using `application.properties` now
2. ✅ **Commented out Kafka config** - App can start without Kafka
3. ✅ **Made Kafka optional** - Added conditional annotations

## Before Running

### 1. Start MySQL in XAMPP
- Open XAMPP Control Panel
- Click "Start" next to MySQL
- MySQL should be running on port 3306

### 2. Create Database
Open phpMyAdmin (http://localhost/phpmyadmin) or MySQL command line:

```sql
CREATE DATABASE billingdb;
```

### 3. Verify Database Connection
The app is configured to connect to:
- **Host**: localhost:3306
- **Database**: billingdb
- **Username**: root
- **Password**: (empty - XAMPP default)

If your XAMPP MySQL has a password, update `src/main/resources/application.properties`:
```properties
spring.datasource.password=your_password
```

## Now Try Running Again

```bash
./mvnw spring-boot:run
```

The application should start successfully!

## If Still Getting Errors

1. **Check MySQL is running**: Look for green "Running" status in XAMPP
2. **Verify database exists**: Check phpMyAdmin
3. **Check port 3306**: Make sure nothing else is using it
4. **Rebuild project**: `./mvnw clean install`

## Kafka (Optional)

Kafka configuration is now commented out. If you want to use Kafka:
1. Uncomment the Kafka lines in `application.properties`
2. Start Kafka on localhost:9092
3. Restart the application

