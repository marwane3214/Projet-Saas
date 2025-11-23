# Local Development Setup (Without Docker)

This guide will help you set up and run the Billing Service locally without Docker.

## Prerequisites

1. **Java 17** - Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/#java17)
2. **Maven 3.6+** - Or use the included Maven wrapper (`mvnw`)
3. **XAMPP with MySQL** - [Download XAMPP](https://www.apachefriends.org/) (includes MySQL)
4. **Kafka** - [Download Kafka](https://kafka.apache.org/downloads) (or use a simpler alternative)

## Step 1: Install and Setup MySQL (XAMPP)

### Windows:
1. Download and install XAMPP from https://www.apachefriends.org/
2. Start XAMPP Control Panel
3. Start MySQL service (click "Start" next to MySQL)
4. MySQL will run on port 3306

### Create Database:
```sql
-- Option 1: Using phpMyAdmin (http://localhost/phpmyadmin)
-- Click "New" and create database named "billingdb"

-- Option 2: Using MySQL command line
mysql -u root -p
CREATE DATABASE billingdb;
```

### Update Credentials:
If your MySQL username/password differs, update `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password  # Leave empty if no password
```

## Step 2: Install and Setup Kafka

### Option A: Using Kafka (Full Setup)

1. Download Kafka from https://kafka.apache.org/downloads
2. Extract and navigate to the Kafka directory
3. Start Zookeeper:
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```
4. Start Kafka (in a new terminal):
```bash
bin/kafka-server-start.sh config/server.properties
```

### Option B: Skip Kafka (For Testing Without Events)

If you want to run without Kafka initially, you can comment out Kafka dependencies temporarily, but the service will start without event publishing.

## Step 3: Build the Project

```bash
# Using Maven wrapper (recommended)
./mvnw clean install

# Or using system Maven
mvn clean install
```

## Step 4: Run the Application

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using system Maven
mvn spring-boot:run

# Or run the JAR directly (after building)
java -jar target/service-facturation-0.0.1-SNAPSHOT.jar
```

## Step 5: Verify It's Running

1. **Health Check**: http://localhost:8080/actuator/health
2. **Swagger UI**: http://localhost:8080/swagger-ui.html
3. **API Docs**: http://localhost:8080/api-docs

## Troubleshooting

### MySQL Connection Issues
- Ensure MySQL is running in XAMPP Control Panel
- Verify database exists: Open phpMyAdmin (http://localhost/phpmyadmin) or use `mysql -u root -e "SHOW DATABASES;"`
- Check connection string in `application.properties`
- Default XAMPP MySQL: username=`root`, password=empty (no password)

### Kafka Connection Issues
- Ensure Kafka is running on port 9092
- Check Kafka logs for errors
- The service will start even if Kafka is unavailable, but events won't be published

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`
- Or stop the process using port 8080

### Java Version Issues
- Verify Java 17: `java -version`
- Set JAVA_HOME if needed
- Ensure IDE is configured for Java 17

## Quick Start (Minimal Setup)

If you just want to test the API without Kafka:

1. Install PostgreSQL and create database
2. Update `application.properties` with your PostgreSQL credentials
3. Comment out Kafka listeners temporarily (or leave them - they'll just log errors)
4. Run: `./mvnw spring-boot:run`

The service will work for API calls, but Kafka events won't be published.

## Development Tips

- **Hot Reload**: Use Spring Boot DevTools (already in dependencies) - just restart the app
- **Database Migrations**: Flyway will automatically run migrations on startup
- **Logs**: Check console output for detailed logs
- **Testing**: Run `./mvnw test` to execute unit tests

