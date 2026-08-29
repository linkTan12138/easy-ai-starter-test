@echo off
cd /d "C:\Users\18275\Desktop\link\project\easy-ai-starter-test"
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
start "easy-ai-test" java -jar target\easy-ai-starter-test-1.0.0.jar
