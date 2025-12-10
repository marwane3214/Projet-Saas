# gRPC Testing Guide for Notification Service

## Overview
The notification-service now supports both REST API (port 8084) and gRPC (port 9090).

## gRPC Server Configuration
- **Port**: 9090
- **Protocol**: HTTP/2
- **Serialization**: Protocol Buffers

## Available gRPC Services

1. **NotificationTemplateService** - Template management
2. **NotificationService** - Notification records
3. **ChannelConfigService** - Channel configuration
4. **SubscriberPreferencesService** - Customer preferences

## How to Test gRPC

### Option 1: Using grpcurl (Recommended)

1. **Install grpcurl**:
   - Windows: Download from https://github.com/fullstorydev/grpcurl/releases
   - Or use: `go install github.com/fullstorydev/grpcurl/cmd/grpcurl@latest`

2. **List available services**:
   ```bash
   grpcurl -plaintext localhost:9090 list
   ```

3. **List methods for a service**:
   ```bash
   grpcurl -plaintext localhost:9090 list com.saas.notificationservice.proto.NotificationTemplateService
   ```

4. **Call a method** (Create Template):
   ```bash
   grpcurl -plaintext -d '{
     "name": "Welcome Email",
     "subject": "Welcome!",
     "body": "Welcome to our service",
     "type": "TEMPLATE_EMAIL"
   }' localhost:9090 com.saas.notificationservice.proto.NotificationTemplateService/CreateTemplate
   ```

5. **Get all templates**:
   ```bash
   grpcurl -plaintext localhost:9090 com.saas.notificationservice.proto.NotificationTemplateService/GetAllTemplates
   ```

### Option 2: Using BloomRPC (GUI Tool)

1. Download BloomRPC from: https://github.com/uw-labs/bloomrpc
2. Import proto files from `src/main/proto/`
3. Connect to `localhost:9090`
4. Test methods interactively

### Option 3: Using Postman (with gRPC support)

1. Use Postman's gRPC feature
2. Import proto files
3. Connect to `localhost:9090`
4. Test methods

## Verify gRPC Server is Running

1. **Check if port 9090 is listening**:
   ```bash
   netstat -ano | findstr :9090
   ```

2. **Check application logs** for:
   ```
   gRPC Server started on port 9090
   ```

3. **Test with grpcurl**:
   ```bash
   grpcurl -plaintext localhost:9090 list
   ```
   Should return list of available services.

## Example gRPC Calls

### Create Notification Template
```bash
grpcurl -plaintext -d '{
  "name": "Test Template",
  "subject": "Test Subject",
  "body": "Test Body",
  "type": "TEMPLATE_EMAIL"
}' localhost:9090 com.saas.notificationservice.proto.NotificationTemplateService/CreateTemplate
```

### Create Notification
```bash
grpcurl -plaintext -d '{
  "customerId": 1,
  "templateId": 1,
  "channel": "CHANNEL_EMAIL",
  "status": "STATUS_QUEUED",
  "payload": "{\"key\":\"value\"}"
}' localhost:9090 com.saas.notificationservice.proto.NotificationService/CreateNotification
```

### Create Channel Config
```bash
grpcurl -plaintext -d '{
  "channel": "CHANNEL_EMAIL",
  "configJson": "{\"host\":\"smtp.example.com\",\"port\":587}",
  "isActive": true
}' localhost:9090 com.saas.notificationservice.proto.ChannelConfigService/CreateChannelConfig
```

### Create Subscriber Preferences
```bash
grpcurl -plaintext -d '{
  "customerId": 1,
  "emailEnabled": true,
  "smsEnabled": false,
  "pushEnabled": true,
  "language": "en"
}' localhost:9090 com.saas.notificationservice.proto.SubscriberPreferencesService/CreatePreferences
```

## Enum Values

- **TemplateTypeProto**: TEMPLATE_EMAIL, TEMPLATE_SMS, TEMPLATE_PUSH
- **NotificationChannelProto**: CHANNEL_EMAIL, CHANNEL_SMS, CHANNEL_PUSH
- **NotificationStatusProto**: STATUS_QUEUED, STATUS_SENT, STATUS_FAILED

## Troubleshooting

1. **Port already in use**: Change port in `application.properties`:
   ```
   grpc.server.port=9091
   ```

2. **Connection refused**: Ensure application is running and gRPC server started successfully

3. **Method not found**: Check service name and method name are correct (case-sensitive)

4. **Proto file errors**: Ensure proto files are compiled correctly:
   ```bash
   mvn clean compile
   ```

