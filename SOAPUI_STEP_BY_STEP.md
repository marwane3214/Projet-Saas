# SoapUI Step-by-Step Guide

## 🎯 Quick Start (5 Minutes)

### Step 1: Open SoapUI
Launch SoapUI application

### Step 2: Create New SOAP Project
1. Click **File → New SOAP Project** (or press `Ctrl+N`)
2. In the dialog:
   - **Project Name:** `Billing Service`
   - **Initial WSDL:** `http://localhost:8080/ws/invoices.wsdl`
   - ✅ Check "Create Requests" (should be checked by default)
3. Click **OK**

SoapUI will automatically:
- Download the WSDL
- Create 4 request templates
- Set up all operations

---

## Step 3: Test createInvoiceRequest

1. **Expand the project** in left panel
2. **Expand "invoices"** binding
3. **Click on "createInvoiceRequest"**

### Fill in the Request Form:

In the request editor, you'll see form fields. Fill them:

| Field | Value | Required? |
|-------|-------|-----------|
| `customerId` | `1` | ✅ Yes |
| `subscriptionId` | `1` | ✅ Yes |
| `amount` | `100.50` | ✅ Yes |
| `currency` | `USD` | ❌ No (defaults to USD) |
| `dueDate` | `2024-12-31` | ✅ Yes |
| `notes` | `Test invoice` | ❌ No |
| `idempotencyKey` | (leave empty) | ❌ No |

### Submit Request:
1. Click the **green "Submit" button** (play icon) at the top
2. Or press `F5`

### Check Response:
- Look at the **Response** panel on the right
- You should see XML with invoice details
- **Save the `<id>` value** from response (e.g., `1`)

---

## Step 4: Test getInvoiceRequest

1. **Click on "getInvoiceRequest"** in left panel

### Fill in:
- `id`: `1` (use the ID from createInvoiceRequest response)

### Submit:
- Click **Submit** button

### Expected:
- Invoice details in XML response

---

## Step 5: Test getInvoicesByCustomerRequest

1. **Click on "getInvoicesByCustomerRequest"**

### Fill in:
- `customerId`: `1`

### Submit:
- Click **Submit**

### Expected:
- List of invoices for customer 1

---

## Step 6: Test markInvoiceAsPaidRequest

1. **Click on "markInvoiceAsPaidRequest"**

### Fill in:
- `id`: `1` (use invoice ID from step 3)

### Submit:
- Click **Submit**

### Expected:
- Invoice with status changed to "PAID"

---

## 📋 Complete Form Values Summary

### createInvoiceRequest:
```
customerId: 1
subscriptionId: 1
amount: 100.50
currency: USD
dueDate: 2024-12-31
notes: Test invoice from SoapUI
```

### getInvoiceRequest:
```
id: 1
```

### getInvoicesByCustomerRequest:
```
customerId: 1
```

### markInvoiceAsPaidRequest:
```
id: 1
```

---

## ✅ Success Indicators

- ✅ WSDL loads successfully
- ✅ All 4 operations appear in SoapUI
- ✅ createInvoiceRequest returns invoice with ID
- ✅ getInvoiceRequest returns invoice details
- ✅ getInvoicesByCustomerRequest returns list
- ✅ markInvoiceAsPaidRequest changes status to PAID

---

## 🔧 If WSDL Doesn't Load

1. **Check application is running:**
   - Open: http://localhost:8080/actuator/health
   - Should return: `{"status":"UP"}`

2. **Verify WSDL URL:**
   - Open: http://localhost:8080/ws/invoices.wsdl
   - Should show XML

3. **Check SoapUI error message:**
   - Look at bottom status bar
   - Check "Errors" tab

---

## 💡 Tips

- **Save requests:** Right-click request → "Save Request"
- **Create test suite:** Right-click project → "New TestSuite"
- **Assertions:** Add assertions to validate responses
- **Properties:** Use properties for dynamic values

---

**That's all! Just fill in the form fields and click Submit!** 🎉

