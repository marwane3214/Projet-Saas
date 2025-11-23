# Fix: StringConcatFactory Error

## Problem
```
The type java.lang.invoke.StringConcatFactory cannot be resolved
```

This error occurs because your IDE is using **Java 8**, but the project requires **Java 17**.

## Solution

### Step 1: Install Java 17
1. Download Java 17 from: https://adoptium.net/temurin/releases/?version=17
2. Install it (remember the installation path, e.g., `C:\Program Files\Java\jdk-17`)

### Step 2: Configure Your IDE

#### For VS Code / Cursor:
1. Open Command Palette (`Ctrl+Shift+P`)
2. Type: `Java: Configure Java Runtime`
3. Click `+` to add a new runtime
4. Set path to your Java 17 installation (e.g., `C:\Program Files\Java\jdk-17`)
5. Set it as default
6. Reload window: `Ctrl+Shift+P` → `Developer: Reload Window`

#### Alternative (Manual):
1. Open `.vscode/settings.json` (already created)
2. Update the path to your Java 17 installation:
```json
{
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-17",
            "path": "C:\\Program Files\\Java\\jdk-17",  // UPDATE THIS PATH
            "default": true
        }
    ]
}
```

### Step 3: Verify Java Version
```bash
java -version
# Should show: java version "17.x.x"
```

### Step 4: Clean and Rebuild
```bash
# Clean Maven project
./mvnw clean

# Rebuild
./mvnw compile
```

### Step 5: Reload IDE
- **VS Code/Cursor**: `Ctrl+Shift+P` → `Developer: Reload Window`
- **IntelliJ**: File → Invalidate Caches → Restart
- **Eclipse**: Project → Clean → Clean all projects

## Quick Check
After fixing, the error should disappear. If it persists:
1. Check `java -version` shows 17
2. Verify IDE Java settings point to Java 17
3. Clean and rebuild the project
4. Restart IDE

## Why This Happens
- `StringConcatFactory` was introduced in Java 9
- Lombok uses it when generating code
- Your IDE was compiling with Java 8, which doesn't have this class
- Solution: Use Java 17 (as required by Spring Boot 3.x)

