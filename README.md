# Compile

```bash
javac -cp "libs/*" -d out -sourcepath src $(find src -name "*.java")
```

# Run

```bash
java -cp "libs/*:out" com.jobportal.Main
```
