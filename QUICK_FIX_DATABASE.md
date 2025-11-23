# Quick Fix: Create Database

## The Error
```
Unknown database 'billingdb'
```

## Solution: Create the Database

### Method 1: Using phpMyAdmin (Easiest)

1. **Open phpMyAdmin**: http://localhost/phpmyadmin
2. **Click "New"** in the left sidebar
3. **Enter database name**: `billingdb`
4. **Click "Create"**
5. **Done!** Now run your application again

### Method 2: Using MySQL Command Line

1. Open Command Prompt or PowerShell
2. Navigate to XAMPP MySQL bin folder (usually `C:\xampp\mysql\bin`)
3. Run:
```bash
mysql -u root
```
4. Then in MySQL prompt:
```sql
CREATE DATABASE billingdb;
exit;
```

### Method 3: Using the Script

Run the batch file:
```bash
create-database.bat
```

Or manually run the SQL file in phpMyAdmin:
- Open phpMyAdmin
- Click "SQL" tab
- Copy and paste contents of `create-database.sql`
- Click "Go"

## After Creating Database

Run your application again:
```bash
./mvnw spring-boot:run
```

The application should now start successfully!

## Verify Database Exists

In phpMyAdmin, you should see `billingdb` in the left sidebar after creating it.

