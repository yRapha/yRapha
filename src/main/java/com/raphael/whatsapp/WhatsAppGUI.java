package com.raphael.whatsapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interface gráfica para o WhatsApp Automation
 * Fornece uma interface simples com botões para executar as funcionalidades
 */
public class WhatsAppGUI extends JFrame {
    
    private JButton btnOpenWhatsApp;
    private JButton btnStartBot;
    private JButton btnSendMessage;
    private JTextArea textArea;
    private JTextField contactField;
    private JTextField messageField;
    
    public WhatsAppGUI() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("WhatsApp Automation - by Raphael");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Layout principal
        setLayout(new BorderLayout());
        
        // Painel superior com título
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(37, 211, 102)); // Verde WhatsApp
        JLabel titleLabel = new JLabel("WhatsApp Automation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel central com botões e campos
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Botão para abrir WhatsApp
        btnOpenWhatsApp = new JButton("🚀 Abrir WhatsApp Web");
        btnOpenWhatsApp.setFont(new Font("Arial", Font.BOLD, 16));
        btnOpenWhatsApp.setBackground(new Color(37, 211, 102));
        btnOpenWhatsApp.setForeground(Color.WHITE);
        btnOpenWhatsApp.setPreferredSize(new Dimension(250, 50));
        btnOpenWhatsApp.addActionListener(new OpenWhatsAppListener());
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(btnOpenWhatsApp, gbc);
        
        // Campo para contato
        JLabel contactLabel = new JLabel("Nome do Contato:");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(contactLabel, gbc);
        
        contactField = new JTextField(20);
        contactField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(contactField, gbc);
        
        // Campo para mensagem
        JLabel messageLabel = new JLabel("Mensagem:");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(messageLabel, gbc);
        
        messageField = new JTextField(20);
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(messageField, gbc);
        
        // Botão para enviar mensagem
        btnSendMessage = new JButton("📱 Enviar Mensagem");
        btnSendMessage.setFont(new Font("Arial", Font.BOLD, 14));
        btnSendMessage.setBackground(new Color(34, 139, 34));
        btnSendMessage.setForeground(Color.WHITE);
        btnSendMessage.setPreferredSize(new Dimension(200, 40));
        btnSendMessage.addActionListener(new SendMessageListener());
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(btnSendMessage, gbc);
        
        // Botão para iniciar bot interativo
        btnStartBot = new JButton("🤖 Iniciar Bot Interativo");
        btnStartBot.setFont(new Font("Arial", Font.BOLD, 14));
        btnStartBot.setBackground(new Color(255, 140, 0));
        btnStartBot.setForeground(Color.WHITE);
        btnStartBot.setPreferredSize(new Dimension(200, 40));
        btnStartBot.addActionListener(new StartBotListener());
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(btnStartBot, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Área de texto para logs
        textArea = new JTextArea(8, 50);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Log da Aplicação"));
        add(scrollPane, BorderLayout.SOUTH);
        
        // Mensagem inicial
        appendLog("WhatsApp Automation iniciado!");
        appendLog("Clique em 'Abrir WhatsApp Web' para começar.");
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + message + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
    
    // Listener para abrir WhatsApp
    private class OpenWhatsAppListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            appendLog("Abrindo WhatsApp Web...");
            btnOpenWhatsApp.setEnabled(false);
            
            new Thread(() -> {
                try {
                    WhatsAppAutomation automation = new WhatsAppAutomation();
                    automation.openWhatsApp();
                    SwingUtilities.invokeLater(() -> {
                        appendLog("WhatsApp Web aberto! Escaneie o QR Code para fazer login.");
                        btnSendMessage.setEnabled(true);
                        btnStartBot.setEnabled(true);
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        appendLog("Erro ao abrir WhatsApp: " + ex.getMessage());
                        btnOpenWhatsApp.setEnabled(true);
                    });
                }
            }).start();
        }
    }
    
    // Listener para enviar mensagem
    private class SendMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String contact = contactField.getText().trim();
            String message = messageField.getText().trim();
            
            if (contact.isEmpty() || message.isEmpty()) {
                JOptionPane.showMessageDialog(WhatsAppGUI.this, 
                    "Por favor, preencha o nome do contato e a mensagem!", 
                    "Campos obrigatórios", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            appendLog("Enviando mensagem para: " + contact);
            btnSendMessage.setEnabled(false);
            
            new Thread(() -> {
                try {
                    WhatsAppAutomation automation = new WhatsAppAutomation();
                    automation.sendMessageToContact(contact, message);
                    SwingUtilities.invokeLater(() -> {
                        appendLog("Mensagem enviada com sucesso para: " + contact);
                        contactField.setText("");
                        messageField.setText("");
                        btnSendMessage.setEnabled(true);
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        appendLog("Erro ao enviar mensagem: " + ex.getMessage());
                        btnSendMessage.setEnabled(true);
                    });
                }
            }).start();
        }
    }
    
    // Listener para iniciar bot
    private class StartBotListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            appendLog("Iniciando bot interativo...");
            btnStartBot.setEnabled(false);
            
            new Thread(() -> {
                try {
                    WhatsAppBot bot = new WhatsAppBot();
                    bot.start();
                    SwingUtilities.invokeLater(() -> {
                        appendLog("Bot interativo iniciado! Verifique o console para interação.");
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        appendLog("Erro ao iniciar bot: " + ex.getMessage());
                        btnStartBot.setEnabled(true);
                    });
                }
            }).start();
        }
    }
    
    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new WhatsAppGUI().setVisible(true);
        });
    }
}