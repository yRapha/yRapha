package com.raphael.whatsapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WhatsAppBot {
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppBot.class);
    private WhatsAppAutomation automation;
    private ScheduledExecutorService scheduler;
    private Scanner scanner;
    
    public WhatsAppBot() {
        this.automation = new WhatsAppAutomation();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        try {
            automation.openWhatsApp();
            Thread.sleep(3000); // Aguardar carregamento completo
            
            System.out.println("=== WhatsApp Bot Iniciado ===");
            showMenu();
            
        } catch (Exception e) {
            logger.error("Erro ao iniciar bot: " + e.getMessage());
        }
    }
    
    private void showMenu() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Enviar mensagem simples");
            System.out.println("2. Enviar múltiplas mensagens");
            System.out.println("3. Enviar mensagem para múltiplos contatos");
            System.out.println("4. Agendar mensagem");
            System.out.println("5. Modo interativo");
            System.out.println("6. Enviar mensagem de bom dia automática");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            
            String choice = scanner.nextLine().trim();
            
            try {
                switch (choice) {
                    case "1":
                        sendSimpleMessage();
                        break;
                    case "2":
                        sendMultipleMessages();
                        break;
                    case "3":
                        sendMessageToMultipleContacts();
                        break;
                    case "4":
                        scheduleMessage();
                        break;
                    case "5":
                        automation.interactiveMode();
                        break;
                    case "6":
                        sendGoodMorningMessage();
                        break;
                    case "7":
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
    
    private void sendSimpleMessage() {
        System.out.print("Digite o nome do contato: ");
        String contact = scanner.nextLine().trim();
        
        System.out.print("Digite a mensagem: ");
        String message = scanner.nextLine().trim();
        
        if (!contact.isEmpty() && !message.isEmpty()) {
            automation.sendMessageToContact(contact, message);
            System.out.println("Mensagem enviada com sucesso!");
        } else {
            System.out.println("Nome do contato e mensagem são obrigatórios!");
        }
    }
    
    private void sendMultipleMessages() {
        System.out.print("Digite o nome do contato: ");
        String contact = scanner.nextLine().trim();
        
        System.out.println("Digite as mensagens (digite 'FIM' para finalizar):");
        List<String> messages = new java.util.ArrayList<>();
        
        while (true) {
            String message = scanner.nextLine().trim();
            if ("FIM".equalsIgnoreCase(message)) {
                break;
            }
            if (!message.isEmpty()) {
                messages.add(message);
            }
        }
        
        if (!contact.isEmpty() && !messages.isEmpty()) {
            automation.sendMultipleMessages(contact, messages.toArray(new String[0]));
            System.out.println("Mensagens enviadas com sucesso!");
        } else {
            System.out.println("Nome do contato e pelo menos uma mensagem são obrigatórios!");
        }
    }
    
    private void sendMessageToMultipleContacts() {
        System.out.print("Digite a mensagem: ");
        String message = scanner.nextLine().trim();
        
        System.out.println("Digite os nomes dos contatos (digite 'FIM' para finalizar):");
        List<String> contacts = new java.util.ArrayList<>();
        
        while (true) {
            String contact = scanner.nextLine().trim();
            if ("FIM".equalsIgnoreCase(contact)) {
                break;
            }
            if (!contact.isEmpty()) {
                contacts.add(contact);
            }
        }
        
        if (!message.isEmpty() && !contacts.isEmpty()) {
            for (String contact : contacts) {
                try {
                    automation.sendMessageToContact(contact, message);
                    System.out.println("Mensagem enviada para: " + contact);
                    Thread.sleep(2000); // Aguardar entre contatos
                } catch (Exception e) {
                    System.err.println("Erro ao enviar para " + contact + ": " + e.getMessage());
                }
            }
            System.out.println("Processo concluído!");
        } else {
            System.out.println("Mensagem e pelo menos um contato são obrigatórios!");
        }
    }
    
    private void scheduleMessage() {
        System.out.print("Digite o nome do contato: ");
        String contact = scanner.nextLine().trim();
        
        System.out.print("Digite a mensagem: ");
        String message = scanner.nextLine().trim();
        
        System.out.print("Digite o delay em segundos: ");
        try {
            int delay = Integer.parseInt(scanner.nextLine().trim());
            
            if (!contact.isEmpty() && !message.isEmpty() && delay > 0) {
                System.out.println("Mensagem agendada para " + delay + " segundos...");
                
                scheduler.schedule(() -> {
                    try {
                        automation.sendMessageToContact(contact, message);
                        System.out.println("\nMensagem agendada enviada para " + contact);
                    } catch (Exception e) {
                        System.err.println("Erro ao enviar mensagem agendada: " + e.getMessage());
                    }
                }, delay, TimeUnit.SECONDS);
                
                System.out.println("Mensagem agendada com sucesso!");
            } else {
                System.out.println("Dados inválidos!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Delay deve ser um número válido!");
        }
    }
    
    private void sendGoodMorningMessage() {
        System.out.println("Digite os contatos para receber bom dia (digite 'FIM' para finalizar):");
        List<String> contacts = new java.util.ArrayList<>();
        
        while (true) {
            String contact = scanner.nextLine().trim();
            if ("FIM".equalsIgnoreCase(contact)) {
                break;
            }
            if (!contact.isEmpty()) {
                contacts.add(contact);
            }
        }
        
        if (!contacts.isEmpty()) {
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            String goodMorningMessage = String.format(
                "🌅 Bom dia! 🌅\n" +
                "Espero que você tenha um dia maravilhoso! ☀️\n" +
                "Enviado às %s", currentTime
            );
            
            for (String contact : contacts) {
                try {
                    automation.sendMessageToContact(contact, goodMorningMessage);
                    System.out.println("Bom dia enviado para: " + contact);
                    Thread.sleep(3000); // Aguardar mais tempo entre mensagens de bom dia
                } catch (Exception e) {
                    System.err.println("Erro ao enviar bom dia para " + contact + ": " + e.getMessage());
                }
            }
            System.out.println("Bom dia enviado para todos!");
        } else {
            System.out.println("Nenhum contato especificado!");
        }
    }
    
    public void sendBulkMessages(List<String> contacts, String message) {
        System.out.println("Enviando mensagem em massa para " + contacts.size() + " contatos...");
        
        for (int i = 0; i < contacts.size(); i++) {
            String contact = contacts.get(i);
            try {
                automation.sendMessageToContact(contact, message);
                System.out.println("(" + (i + 1) + "/" + contacts.size() + ") Enviado para: " + contact);
                
                // Aguardar entre mensagens para não ser bloqueado
                Thread.sleep(5000);
                
            } catch (Exception e) {
                System.err.println("Erro ao enviar para " + contact + ": " + e.getMessage());
            }
        }
        
        System.out.println("Envio em massa concluído!");
    }
    
    public void sendReminder(String contact, String message, int intervalMinutes) {
        System.out.println("Configurando lembrete para " + contact + " a cada " + intervalMinutes + " minutos");
        
        scheduler.scheduleAtFixedRate(() -> {
            try {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                String reminderMessage = "🔔 LEMBRETE (" + timestamp + "):\n" + message;
                
                automation.sendMessageToContact(contact, reminderMessage);
                System.out.println("Lembrete enviado para " + contact);
                
            } catch (Exception e) {
                System.err.println("Erro ao enviar lembrete: " + e.getMessage());
            }
        }, 0, intervalMinutes, TimeUnit.MINUTES);
    }
    
    public void close() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        if (automation != null) {
            automation.close();
        }
        if (scanner != null) {
            scanner.close();
        }
    }
    
    public static void main(String[] args) {
        WhatsAppBot bot = null;
        
        try {
            bot = new WhatsAppBot();
            bot.start();
            
        } catch (Exception e) {
            logger.error("Erro na execução do bot: " + e.getMessage(), e);
        } finally {
            if (bot != null) {
                bot.close();
            }
        }
    }
}