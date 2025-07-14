package com.raphael.whatsapp;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de exemplo demonstrando diferentes formas de usar a automação do WhatsApp
 */
public class ExampleUsage {
    
    public static void main(String[] args) {
        System.out.println("=== Exemplos de Uso da Automação WhatsApp ===\n");
        
        // Exemplo 1: Uso básico
        exemploBasico();
        
        // Exemplo 2: Múltiplas mensagens
        exemploMultiplasMensagens();
        
        // Exemplo 3: Mensagem para múltiplos contatos
        exemploMultiplosContatos();
        
        // Exemplo 4: Uso do bot
        exemploBot();
    }
    
    /**
     * Exemplo 1: Uso básico - Enviar uma mensagem simples
     */
    public static void exemploBasico() {
        System.out.println("1. Exemplo Básico:");
        System.out.println("```java");
        System.out.println("WhatsAppAutomation automation = new WhatsAppAutomation();");
        System.out.println("automation.openWhatsApp();");
        System.out.println("automation.sendMessageToContact(\"João\", \"Olá! Como você está?\");");
        System.out.println("automation.close();");
        System.out.println("```\n");
        
        // Código comentado para não executar automaticamente
        /*
        WhatsAppAutomation automation = new WhatsAppAutomation();
        try {
            automation.openWhatsApp();
            automation.sendMessageToContact("João", "Olá! Como você está?");
        } finally {
            automation.close();
        }
        */
    }
    
    /**
     * Exemplo 2: Enviar múltiplas mensagens para um contato
     */
    public static void exemploMultiplasMensagens() {
        System.out.println("2. Múltiplas Mensagens:");
        System.out.println("```java");
        System.out.println("String[] messages = {");
        System.out.println("    \"Olá!\",");
        System.out.println("    \"Como você está?\",");
        System.out.println("    \"Espero que esteja bem!\"");
        System.out.println("};");
        System.out.println("automation.sendMultipleMessages(\"Maria\", messages);");
        System.out.println("```\n");
        
        // Código comentado para não executar automaticamente
        /*
        WhatsAppAutomation automation = new WhatsAppAutomation();
        try {
            automation.openWhatsApp();
            
            String[] messages = {
                "Olá!",
                "Como você está?",
                "Espero que esteja bem!"
            };
            
            automation.sendMultipleMessages("Maria", messages);
        } finally {
            automation.close();
        }
        */
    }
    
    /**
     * Exemplo 3: Enviar mensagem para múltiplos contatos
     */
    public static void exemploMultiplosContatos() {
        System.out.println("3. Múltiplos Contatos:");
        System.out.println("```java");
        System.out.println("List<String> contacts = Arrays.asList(\"João\", \"Maria\", \"Pedro\");");
        System.out.println("String message = \"Mensagem para todos!\";");
        System.out.println("for (String contact : contacts) {");
        System.out.println("    automation.sendMessageToContact(contact, message);");
        System.out.println("    Thread.sleep(2000); // Aguardar entre contatos");
        System.out.println("}");
        System.out.println("```\n");
        
        // Código comentado para não executar automaticamente
        /*
        WhatsAppAutomation automation = new WhatsAppAutomation();
        try {
            automation.openWhatsApp();
            
            List<String> contacts = Arrays.asList("João", "Maria", "Pedro");
            String message = "Mensagem para todos!";
            
            for (String contact : contacts) {
                automation.sendMessageToContact(contact, message);
                Thread.sleep(2000); // Aguardar entre contatos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            automation.close();
        }
        */
    }
    
    /**
     * Exemplo 4: Usar o bot com funcionalidades avançadas
     */
    public static void exemploBot() {
        System.out.println("4. Uso do Bot:");
        System.out.println("```java");
        System.out.println("WhatsAppBot bot = new WhatsAppBot();");
        System.out.println("bot.start(); // Inicia o menu interativo");
        System.out.println("");
        System.out.println("// Envio em massa programático");
        System.out.println("List<String> contacts = Arrays.asList(\"João\", \"Maria\", \"Pedro\");");
        System.out.println("bot.sendBulkMessages(contacts, \"Mensagem em massa!\");");
        System.out.println("");
        System.out.println("// Lembrete a cada 30 minutos");
        System.out.println("bot.sendReminder(\"João\", \"Lembrete importante!\", 30);");
        System.out.println("");
        System.out.println("bot.close();");
        System.out.println("```\n");
        
        // Código comentado para não executar automaticamente
        /*
        WhatsAppBot bot = new WhatsAppBot();
        try {
            bot.start();
            
            // Envio em massa programático
            List<String> contacts = Arrays.asList("João", "Maria", "Pedro");
            bot.sendBulkMessages(contacts, "Mensagem em massa!");
            
            // Lembrete a cada 30 minutos
            bot.sendReminder("João", "Lembrete importante!", 30);
            
        } finally {
            bot.close();
        }
        */
    }
    
    /**
     * Exemplo prático: Mensagem de bom dia automática
     */
    public static void exemploBomdiaAutomatico() {
        System.out.println("5. Bom Dia Automático:");
        System.out.println("```java");
        System.out.println("WhatsAppAutomation automation = new WhatsAppAutomation();");
        System.out.println("automation.openWhatsApp();");
        System.out.println("");
        System.out.println("List<String> amigos = Arrays.asList(\"João\", \"Maria\", \"Pedro\");");
        System.out.println("String bomDia = \"🌅 Bom dia! Tenha um dia maravilhoso! ☀️\";");
        System.out.println("");
        System.out.println("for (String amigo : amigos) {");
        System.out.println("    automation.sendMessageToContact(amigo, bomDia);");
        System.out.println("    Thread.sleep(3000); // Aguardar 3 segundos");
        System.out.println("}");
        System.out.println("```\n");
    }
    
    /**
     * Exemplo prático: Lembrete de reunião
     */
    public static void exemploLembreteReuniao() {
        System.out.println("6. Lembrete de Reunião:");
        System.out.println("```java");
        System.out.println("WhatsAppBot bot = new WhatsAppBot();");
        System.out.println("bot.start();");
        System.out.println("");
        System.out.println("// Lembrete 30 minutos antes da reunião");
        System.out.println("String lembrete = \"🔔 Lembrete: Reunião em 30 minutos!\";");
        System.out.println("bot.sendReminder(\"Equipe\", lembrete, 30);");
        System.out.println("```\n");
    }
    
    /**
     * Dicas importantes
     */
    public static void dicas() {
        System.out.println("💡 Dicas Importantes:");
        System.out.println("- Sempre use try-finally para fechar recursos");
        System.out.println("- Aguarde entre mensagens para evitar bloqueios");
        System.out.println("- Teste com poucos contatos primeiro");
        System.out.println("- Monitore sua conta para evitar restrições");
        System.out.println("- Use delays adequados (2-5 segundos entre mensagens)");
        System.out.println("- Mantenha o programa rodando para manter a sessão");
        System.out.println();
    }
}