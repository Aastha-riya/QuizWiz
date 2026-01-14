# Run QuizWiz - Step by Step

## ⚠️ Important: Manual Steps Required

I cannot run these commands automatically because they require:
- Your MySQL password
- Java and MySQL to be in your system PATH

## 🎯 Here's What YOU Need to Do:

### Step 1: Add MySQL to PATH (One-time setup)

1. Press `Win + X` → Select "System"
2. Click "Advanced system settings"
3. Click "Environment Variables"
4. Find "Path" under "System variables"
5. Click "Edit"
6. Click "New"
7. Add: `C:\Program Files\MySQL\MySQL Server 8.0\bin`
8. Click OK on all dialogs
9. **Close and reopen Command Prompt**

### Step 2: Add Java to PATH (One-time setup)

1. Find your Java installation (e.g., `C:\Program Files\Java\jdk-11\bin`)
2. Follow same steps as above to add Java bin folder to PATH
3. **Close and reopen Command Prompt**

### Step 3: Setup Database

Open **NEW** Command Prompt and run:
```cmd
cd C:\Users\Yash Gupta\IdeaProjects\QuizWiz
mysql -u root -p < database\schema.sql
```
Enter your MySQL password.

### Step 4: Build Project
```cmd
build.bat
```

### Step 5: Deploy to Tomcat

**Option A: Manual Copy**
1. Copy `webapp` folder
2. Paste to `C:\apache-tomcat-9.0.x\webapps\`
3. Rename to `QuizWiz`

**Option B: Using Command**
```cmd
xcopy /E /I webapp "C:\apache-tomcat-9.0.x\webapps\QuizWiz"
```
(Replace `C:\apache-tomcat-9.0.x` with your actual Tomcat path)

### Step 6: Start Tomcat
```cmd
cd C:\apache-tomcat-9.0.x\bin
startup.bat
```

### Step 7: Open Browser
```
http://localhost:8080/QuizWiz/quiz
```

---

## 🚀 Quick Alternative (If PATH is already set)

If Java and MySQL are already in your PATH, just run these commands:

```cmd
cd C:\Users\Yash Gupta\IdeaProjects\QuizWiz
mysql -u root -p < database\schema.sql
build.bat
xcopy /E /I webapp "C:\apache-tomcat-9.0.x\webapps\QuizWiz"
cd C:\apache-tomcat-9.0.x\bin
startup.bat
```

Then open: `http://localhost:8080/QuizWiz/quiz`

---

## ✅ Success Indicators

You'll know it's working when:
- Database setup: No errors, returns to prompt
- Build: Shows "Build Successful!"
- Tomcat: New window opens with logs
- Browser: Quiz page loads with 5 questions and timer

---

## 🆘 If You Get Stuck

Tell me:
- Which step you're on
- What error you see
- And I'll help you fix it!
