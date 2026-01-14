# QuizWiz - Deployment Checklist

## ✅ COMPLETED
- [x] All source code created
- [x] MySQL Connector JAR added
- [x] Project cleaned and organized

## ⏳ TODO (5 Steps, ~6 minutes)

### Step 1: Setup Database (2 min)
```cmd
cd C:\Users\Yash Gupta\IdeaProjects\QuizWiz
mysql -u root -p < database\schema.sql
```
Enter your MySQL password when prompted.

---

### Step 2: Build Project (1 min)
```cmd
build.bat
```
Wait for "Build Successful!" message.

---

### Step 3: Deploy to Tomcat (2 min)
1. Copy `webapp` folder
2. Paste to `C:\apache-tomcat-9.0.x\webapps\`
3. Rename to `QuizWiz`

---

### Step 4: Start Tomcat (1 min)
```cmd
cd C:\apache-tomcat-9.0.x\bin
startup.bat
```
Wait 10-15 seconds.

---

### Step 5: Access Application
Open: `http://localhost:8080/QuizWiz/quiz`

---

## 🎯 Current Status
**Ready for Step 1** - Run the database setup command above!
