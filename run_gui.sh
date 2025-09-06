#!/bin/bash

# Script para executar a interface gráfica do WhatsApp Automation

echo "🚀 Iniciando WhatsApp Automation GUI..."

# Compilar o projeto
echo "📦 Compilando projeto..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Compilação concluída com sucesso!"
    
    # Executar a aplicação GUI
    echo "🖥️  Iniciando interface gráfica..."
    java -cp target/classes:target/dependency/* com.raphael.whatsapp.WhatsAppGUI
else
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi