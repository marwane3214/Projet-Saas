# Billing Service - Microservice

A production-ready microservice for managing invoices, payments, renewals, and credits in a recurring billing platform.

## 🏗️ Architecture

- **Clean Architecture**: Domain, Application, and Infrastructure layers
- **Event-Driven**: Kafka integration for async communication
- **Domain-Driven Design**: Rich domain models with business logic
- **Microservices-Ready**: RESTful API with OpenAPI documentation

## 🚀 Features

### Core Functionality
- ✅ Invoice management (create, retrieve, mark as paid, reconciliation)
- ✅ Payment processing with retry logic
- ✅ Renewal scheduling and batch processing
- ✅ Credit notes issuance and application
- ✅ Idempotency support for critical operations
- ✅ Event-driven architecture with Kafka

### Technical Features
- ✅ MySQL database (XAMPP) with Flyway migrations
- ✅ Kafka event producers and consumers (optional)
- ✅ Scheduled batch jobs for renewal processing
- ✅ Global error handling and validation
- ✅ OpenAPI/Swagger documentation
- ✅ Health checks and monitoring endpoints
- ✅ Local development ready (no Docker required)

## 📋 Prerequisites

- **Java 17 or higher** - [Download Java 17](https://adoptium.net/)
- **Maven 3.6+** (or use included Maven wrapper `mvnw`)
- **XAMPP with MySQL** - [Download XAMPP](https://www.apachefriends.org/) (includes MySQL)
- **Kafka** (optional for event-driven features) - [Download Kafka](https://kafka.apache.org/downloads)

## 🛠️ Local Development Setup

### Step 1: Setup MySQL (XAMPP)

1. Install XAMPP and start MySQL service from XAMPP Control Panel
2. Open phpMyAdmin (http://localhost/phpmyadmin) or use MySQL command line
3. Create the database:
```sql
CREATE DATABASE billingdb;
```

4. Update credentials in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.username=root
spring.datasource.password=your_password  # Leave empty if no password set
```

### Step 2: Setup Kafka (Optional)

If you want event-driven features:

1. Download and extract Kafka
2. Start Zookeeper:
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```
3. Start Kafka (new terminal):
```bash
bin/kafka-server-start.sh config/server.properties
```

**Note**: The service will start without Kafka, but events won't be published.

### Step 3: Build and Run

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Or run the JAR directly
java -jar target/service-facturation-0.0.1-SNAPSHOT.jar
```

The service will be available at: **http://localhost:8080**

### Quick Start (Minimal)

If you just want to test the API:

1. Install PostgreSQL and create `billingdb` database
2. Update PostgreSQL credentials in `application.properties`
3. Run: `./mvnw spring-boot:run`

The service works for API calls even without Kafka (events just won't be published).

📖 **For detailed setup instructions, see [LOCAL_SETUP.md](LOCAL_SETUP.md)**

## 📡 API Endpoints

### Invoices
- `POST /invoices` - Create invoice
- `GET /invoices/{id}` - Get invoice by ID
- `GET /invoices/customer/{customerId}` - List invoices by customer
- `POST /invoices/{id}/pay` - Mark invoice as paid
- `POST /invoices/{id}/reconcile` - Trigger reconciliation

### Payments
- `POST /payments` - Register payment
- `GET /payments/{id}` - Get payment by ID
- `GET /payments/invoice/{invoiceId}` - Get payments by invoice

### Renewals
- `POST /renewals/schedule` - Schedule renewal
- `POST /renewals/process/{id}` - Process renewal
- `GET /renewals/{id}` - Get renewal by ID
- `GET /renewals/subscription/{subscriptionId}` - Get renewals by subscription

### Credits
- `POST /credits` - Issue credit
- `POST /credits/{creditId}/apply/{invoiceId}` - Apply credit to invoice
- `GET /credits/{id}` - Get credit by ID
- `GET /credits/customer/{customerId}` - Get credits by customer

## 📚 API Documentation

Once the service is running, access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 🔄 Kafka Events

### Consumed Events
- `subscription-created` - When a new subscription is created
- `renewal-due` - When a renewal is due
- `payment-failed` - When a payment fails

### Published Events
- `invoice-created` - When an invoice is created
- `payment-processed` - When a payment is processed
- `renewal-processed` - When a renewal is processed

## ⏰ Scheduled Jobs

- **Daily Renewal Processing**: Runs at 00:00 every day to process due renewals
- **Failed Renewal Retry**: Runs every hour to retry failed renewals

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report
```

## 📝 Notes

- **Docker**: Not included - this project is configured for local development with XAMPP MySQL
- **Database**: Uses MySQL (XAMPP) - make sure MySQL service is running in XAMPP Control Panel
- **Kafka**: Optional - service works without it, but events won't be published

## 📊 Monitoring

- **Health Check**: http://localhost:8080/actuator/health
- **Metrics**: http://localhost:8080/actuator/metrics
- **Info**: http://localhost:8080/actuator/info

## 🔧 Configuration

Key configuration properties in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/billingdb

# Kafka
spring.kafka.bootstrap-servers=localhost:9092

# Server
server.port=8080
```

## 🏛️ Project Structure

```
src/
├── main/
│   ├── java/com/example/billing/
│   │   ├── application/          # Application layer (DTOs, Mappers)
│   │   ├── domain/               # Domain models
│   │   ├── infrastructure/       # Infrastructure (Kafka, DB, etc.)
│   │   ├── repository/           # Data access
│   │   ├── service/              # Business logic
│   │   └── web/                  # Controllers
│   └── resources/
│       ├── db/migration/         # Flyway migrations
│       └── application.properties
└── test/                         # Tests
```

## 🚦 Status

✅ Production-ready with:
- Complete API implementation
- Kafka integration
- Database migrations
- Batch scheduling
- Error handling
- Validation
- Idempotency
- Docker support
- Tests

## 📝 License

This project is part of a microservices architecture demonstration.

