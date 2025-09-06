package com.raphael.whatsapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class WhatsAppAutomation {
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppAutomation.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private Scanner scanner;
    
    // Seletores CSS para elementos do WhatsApp Web
    private static final String SEARCH_BOX = "div[contenteditable='true'][data-tab='3']";
    private static final String MESSAGE_BOX = "div[contenteditable='true'][data-tab='10']";
    private static final String CONTACT_LIST = "div[role='listitem']";
    private static final String SEND_BUTTON = "span[data-icon='send']";
    private static final String CHAT_TITLE = "header span[title]";
    
    public WhatsAppAutomation() {
        this.scanner = new Scanner(System.in);
        initializeWebDriver();
    }
    
    private void initializeWebDriver() {
        try {
            // Configurar WebDriverManager para gerenciar automaticamente o ChromeDriver
            WebDriverManager.chromedriver().setup();
            
            // Configurar opções do Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--user-data-dir=./chrome-data"); // Mantém sessão do WhatsApp
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-debugging-port=9222");
            
            // Inicializar driver e wait
            this.driver = new ChromeDriver(options);
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            logger.info("WebDriver inicializado com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao inicializar WebDriver: " + e.getMessage());
            throw new RuntimeException("Falha ao inicializar WebDriver", e);
        }
    }
    
    public void openWhatsApp() {
        try {
            logger.info("Abrindo WhatsApp Web...");
            driver.get("https://web.whatsapp.com");
            
            // Aguardar carregamento da página
            logger.info("Aguardando carregamento do WhatsApp Web...");
            logger.info("Por favor, escaneie o código QR no seu telefone se necessário");
            
            // Aguardar até que a caixa de pesquisa apareça (indica que o usuário fez login)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SEARCH_BOX)));
            logger.info("WhatsApp Web carregado com sucesso!");
            
        } catch (Exception e) {
            logger.error("Erro ao abrir WhatsApp Web: " + e.getMessage());
            throw new RuntimeException("Falha ao abrir WhatsApp Web", e);
        }
    }
    
    public void searchContact(String contactName) {
        try {
            logger.info("Procurando contato: " + contactName);
            
            // Encontrar caixa de pesquisa
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SEARCH_BOX)));
            
            // Limpar e digitar nome do contato
            searchBox.clear();
            searchBox.sendKeys(contactName);
            
            // Aguardar resultados da pesquisa
            Thread.sleep(2000);
            
            // Procurar e clicar no contato
            List<WebElement> contacts = driver.findElements(By.cssSelector(CONTACT_LIST));
            
            boolean contactFound = false;
            for (WebElement contact : contacts) {
                try {
                    String contactText = contact.getText().toLowerCase();
                    if (contactText.contains(contactName.toLowerCase())) {
                        contact.click();
                        contactFound = true;
                        logger.info("Contato encontrado e selecionado: " + contactName);
                        break;
                    }
                } catch (Exception e) {
                    // Ignorar erros ao processar contatos individuais
                }
            }
            
            if (!contactFound) {
                logger.warn("Contato não encontrado: " + contactName);
            }
            
        } catch (Exception e) {
            logger.error("Erro ao procurar contato: " + e.getMessage());
            throw new RuntimeException("Falha ao procurar contato", e);
        }
    }
    
    public void sendMessage(String message) {
        try {
            if (StringUtils.isBlank(message)) {
                logger.warn("Mensagem vazia não será enviada");
                return;
            }
            
            logger.info("Enviando mensagem: " + message);
            
            // Encontrar caixa de mensagem
            WebElement messageBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MESSAGE_BOX)));
            
            // Digitar mensagem
            messageBox.clear();
            messageBox.sendKeys(message);
            
            // Aguardar um pouco para garantir que a mensagem foi digitada
            Thread.sleep(500);
            
            // Enviar mensagem (Enter)
            messageBox.sendKeys(Keys.ENTER);
            
            logger.info("Mensagem enviada com sucesso!");
            
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem: " + e.getMessage());
            throw new RuntimeException("Falha ao enviar mensagem", e);
        }
    }
    
    public void sendMessageToContact(String contactName, String message) {
        try {
            searchContact(contactName);
            Thread.sleep(1000); // Aguardar carregamento do chat
            sendMessage(message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para contato: " + e.getMessage());
            throw new RuntimeException("Falha ao enviar mensagem para contato", e);
        }
    }
    
    public void sendMultipleMessages(String contactName, String[] messages) {
        try {
            searchContact(contactName);
            Thread.sleep(1000);
            
            for (String message : messages) {
                sendMessage(message);
                Thread.sleep(1000); // Aguardar entre mensagens
            }
        } catch (Exception e) {
            logger.error("Erro ao enviar múltiplas mensagens: " + e.getMessage());
            throw new RuntimeException("Falha ao enviar múltiplas mensagens", e);
        }
    }
    
    public String getCurrentChatTitle() {
        try {
            WebElement titleElement = driver.findElement(By.cssSelector(CHAT_TITLE));
            return titleElement.getAttribute("title");
        } catch (NoSuchElementException e) {
            return "Nenhum chat selecionado";
        }
    }
    
    public void interactiveMode() {
        System.out.println("\n=== MODO INTERATIVO ===");
        System.out.println("Comandos disponíveis:");
        System.out.println("1. buscar <nome> - Buscar contato");
        System.out.println("2. enviar <mensagem> - Enviar mensagem para o chat atual");
        System.out.println("3. chat <nome> <mensagem> - Enviar mensagem para contato específico");
        System.out.println("4. status - Mostrar chat atual");
        System.out.println("5. sair - Sair do programa");
        System.out.println();
        
        while (true) {
            System.out.print("WhatsApp> ");
            String input = scanner.nextLine().trim();
            
            if (input.equals("sair")) {
                break;
            }
            
            try {
                processCommand(input);
            } catch (Exception e) {
                System.err.println("Erro ao processar comando: " + e.getMessage());
            }
        }
    }
    
    private void processCommand(String command) {
        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();
        
        switch (action) {
            case "buscar":
                if (parts.length > 1) {
                    searchContact(parts[1]);
                } else {
                    System.out.println("Uso: buscar <nome>");
                }
                break;
                
            case "enviar":
                if (parts.length > 1) {
                    sendMessage(parts[1]);
                } else {
                    System.out.println("Uso: enviar <mensagem>");
                }
                break;
                
            case "chat":
                if (parts.length > 1) {
                    String[] chatParts = parts[1].split(" ", 2);
                    if (chatParts.length >= 2) {
                        sendMessageToContact(chatParts[0], chatParts[1]);
                    } else {
                        System.out.println("Uso: chat <nome> <mensagem>");
                    }
                } else {
                    System.out.println("Uso: chat <nome> <mensagem>");
                }
                break;
                
            case "status":
                System.out.println("Chat atual: " + getCurrentChatTitle());
                break;
                
            default:
                System.out.println("Comando não reconhecido: " + action);
        }
    }
    
    public void close() {
        if (driver != null) {
            logger.info("Fechando navegador...");
            driver.quit();
        }
        if (scanner != null) {
            scanner.close();
        }
    }
    
    public static void main(String[] args) {
        WhatsAppAutomation automation = null;
        
        try {
            automation = new WhatsAppAutomation();
            automation.openWhatsApp();
            
            // Aguardar um pouco para garantir que tudo carregou
            Thread.sleep(3000);
            
            // Exemplo de uso básico
            System.out.println("WhatsApp Automation iniciado!");
            System.out.println("Você pode usar os seguintes métodos:");
            System.out.println("- automation.sendMessageToContact(\"Nome do Contato\", \"Sua mensagem\");");
            System.out.println("- automation.sendMultipleMessages(\"Nome do Contato\", new String[]{\"Msg 1\", \"Msg 2\"});");
            System.out.println();
            
            // Iniciar modo interativo
            automation.interactiveMode();
            
        } catch (Exception e) {
            logger.error("Erro na execução: " + e.getMessage(), e);
        } finally {
            if (automation != null) {
                automation.close();
            }
        }
    }
}