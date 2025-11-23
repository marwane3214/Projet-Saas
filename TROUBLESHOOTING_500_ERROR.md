# Troubleshooting 500 Internal Server Error

## Common Causes and Solutions

### 1. Database Tables Don't Exist (Most Common)

**Symptom:** 500 error when creating invoice

**Solution:**
1. Check if Flyway migrations ran successfully
2. Verify tables exist in database:
   ```sql
   USE billingdb;
   SHOW TABLES;
   ```
   Should show: `invoices`, `payments`, `renewals`, `credits`

3. If tables don't exist, check application logs for Flyway errors
4. Manually run migrations if needed:
   - Check `src/main/resources/db/migration/` folder
   - Verify migration files exist

### 2. Database Connection Issue

**Check:**
- MySQL is running in XAMPP
- Database `billingdb` exists
- Connection settings in `application.properties` are correct

**Test connection:**
```sql
mysql -u root -e "USE billingdb; SHOW TABLES;"
```

### 3. Check Application Logs

Look for the actual error in your console/logs. The error message will tell you exactly what's wrong.

**Common log messages:**
- `Table 'billingdb.invoices' doesn't exist` → Run migrations
- `Connection refused` → MySQL not running
- `Unknown database` → Database doesn't exist
- `Constraint violation` → Data validation issue

### 4. Verify Request Format

Make sure your JSON is valid:
```json
{
  "customerId": 1,
  "subscriptionId": 1,
  "amount": 100.50,
  "currency": "USD",
  "dueDate": "2024-12-31"
}
```

**Required fields:**
- `customerId` (number)
- `subscriptionId` (number)
- `amount` (number, must be > 0)
- `currency` (string, 3 characters)
- `dueDate` (string, format: YYYY-MM-DD)

### 5. Quick Fix Steps

1. **Restart Application**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Check Database**
   ```sql
   USE billingdb;
   SHOW TABLES;
   SELECT * FROM invoices LIMIT 1;
   ```

3. **Check Logs**
   - Look at console output when you send the request
   - Error details will be logged there

4. **Test with Simple Request**
   ```json
   {
     "customerId": 1,
     "subscriptionId": 1,
     "amount": 100.00,
     "currency": "USD",
     "dueDate": "2024-12-31"
   }
   ```

### 6. Enable Debug Logging

Add to `application.properties`:
```properties
logging.level.com.example.billing=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

This will show detailed error information.

### 7. Common Error Messages

| Error | Solution |
|-------|----------|
| `Table doesn't exist` | Run Flyway migrations or create tables manually |
| `Connection refused` | Start MySQL in XAMPP |
| `Unknown database` | Create database: `CREATE DATABASE billingdb;` |
| `Constraint violation` | Check required fields in request |
| `Null pointer exception` | Check if all dependencies are injected |

## Next Steps

1. Check your application console/logs for the exact error
2. Verify database tables exist
3. Try the request again
4. If still failing, share the exact error message from logs

