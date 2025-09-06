@echo off
title WhatsApp Automation GUI

echo 🚀 Iniciando WhatsApp Automation GUI...

REM Compilar o projeto
echo 📦 Compilando projeto...
call mvn clean compile

if %errorlevel% equ 0 (
    echo ✅ Compilação concluída com sucesso!
    
    REM Executar a aplicação GUI
    echo 🖥️  Iniciando interface gráfica...
    java -cp target/classes;target/dependency/* com.raphael.whatsapp.WhatsAppGUI
) else (
    echo ❌ Erro na compilação. Verifique os logs acima.
    pause
    exit /b 1
)

pause