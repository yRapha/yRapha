# 🤖 WhatsApp Automation Java

Uma aplicação Java completa para automatizar o WhatsApp Web usando Selenium WebDriver.

## 📋 Índice

- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Como Usar](#como-usar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Exemplos de Uso](#exemplos-de-uso)
- [Configuração Avançada](#configuração-avançada)
- [Troubleshooting](#troubleshooting)
- [Contribuições](#contribuições)
- [Avisos Legais](#avisos-legais)

## 🚀 Funcionalidades

### Classe WhatsAppAutomation
- ✅ Abrir WhatsApp Web automaticamente
- ✅ Procurar e selecionar contatos
- ✅ Enviar mensagens individuais
- ✅ Enviar múltiplas mensagens para um contato
- ✅ Modo interativo com comandos
- ✅ Manutenção de sessão (salva login do WhatsApp)
- ✅ Logging detalhado de todas as operações

### Classe WhatsAppBot
- ✅ Menu interativo amigável
- ✅ Envio de mensagens para múltiplos contatos
- ✅ Agendamento de mensagens
- ✅ Mensagens automáticas de bom dia
- ✅ Envio em massa com controle de velocidade
- ✅ Sistema de lembretes periódicos
- ✅ Tratamento de erros robusto

## 🛠️ Pré-requisitos

- **Java 11** ou superior
- **Apache Maven 3.6+**
- **Google Chrome** instalado
- **Conexão com internet** estável
- **Telefone com WhatsApp** para autenticação

## 📦 Instalação

### 1. Clone ou baixe o projeto

```bash
git clone <seu-repositorio>
cd whatsapp-automation
```

### 2. Compile e baixe as dependências

```bash
mvn clean compile
```

### 3. Execute o projeto

```bash
# Usar a classe principal (WhatsAppAutomation)
mvn exec:java -Dexec.mainClass="com.raphael.whatsapp.WhatsAppAutomation"

# Ou usar o bot com menu (WhatsAppBot)
mvn exec:java -Dexec.mainClass="com.raphael.whatsapp.WhatsAppBot"
```

## 🎯 Como Usar

### Primeiro Uso

1. **Execute o programa** - Uma janela do Chrome será aberta
2. **Faça login no WhatsApp Web** - Escaneie o QR code com seu telefone
3. **Aguarde o carregamento** - O programa detectará quando estiver pronto
4. **Comece a usar** - Siga as instruções na tela

### Modo Interativo (WhatsAppAutomation)

```
WhatsApp> buscar João
WhatsApp> enviar Olá, como você está?
WhatsApp> chat Maria Boa tarde!
WhatsApp> status
WhatsApp> sair
```

### Menu do Bot (WhatsAppBot)

```
=== MENU PRINCIPAL ===
1. Enviar mensagem simples
2. Enviar múltiplas mensagens
3. Enviar mensagem para múltiplos contatos
4. Agendar mensagem
5. Modo interativo
6. Enviar mensagem de bom dia automática
7. Sair
```

## 📁 Estrutura do Projeto

```
whatsapp-automation/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── raphael/
│                   └── whatsapp/
│                       ├── WhatsAppAutomation.java
│                       └── WhatsAppBot.java
├── chrome-data/          # Dados do Chrome (criado automaticamente)
├── README.md
└── WHATSAPP_AUTOMATION_README.md
```

## 💡 Exemplos de Uso

### Exemplo 1: Envio Simples

```java
WhatsAppAutomation automation = new WhatsAppAutomation();
automation.openWhatsApp();
automation.sendMessageToContact("João", "Olá! Como você está?");
automation.close();
```

### Exemplo 2: Múltiplas Mensagens

```java
WhatsAppAutomation automation = new WhatsAppAutomation();
automation.openWhatsApp();

String[] messages = {
    "Olá!",
    "Como você está?",
    "Espero que esteja bem!"
};

automation.sendMultipleMessages("Maria", messages);
automation.close();
```

### Exemplo 3: Usar o Bot

```java
WhatsAppBot bot = new WhatsAppBot();
bot.start(); // Inicia o menu interativo

// Envio em massa programático
List<String> contacts = Arrays.asList("João", "Maria", "Pedro");
bot.sendBulkMessages(contacts, "Mensagem para todos!");

// Lembrete a cada 30 minutos
bot.sendReminder("João", "Lembrete importante!", 30);

bot.close();
```

## ⚙️ Configuração Avançada

### Personalizar ChromeOptions

Edite o método `initializeWebDriver()` em `WhatsAppAutomation.java`:

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless"); // Executar sem interface gráfica
options.addArguments("--window-size=1920,1080"); // Tamanho da janela
options.addArguments("--user-agent=Custom User Agent"); // User agent personalizado
```

### Alterar Timeouts

```java
// Aumentar timeout de espera
this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));

// Aumentar delay entre mensagens
Thread.sleep(3000); // 3 segundos
```

### Configurar Logging

Edite o arquivo `src/main/resources/simplelogger.properties`:

```properties
org.slf4j.simpleLogger.defaultLogLevel=INFO
org.slf4j.simpleLogger.showDateTime=true
org.slf4j.simpleLogger.dateTimeFormat=yyyy-MM-dd HH:mm:ss
```

## 🔧 Troubleshooting

### Problema: Chrome não abre

**Solução:**
```bash
# Verificar se o Chrome está instalado
google-chrome --version

# Ou instalar Chrome no Ubuntu/Debian
sudo apt-get install google-chrome-stable
```

### Problema: Elemento não encontrado

**Possíveis causas:**
- WhatsApp Web mudou o layout
- Conexão lenta
- Elemento não carregou ainda

**Solução:**
- Aumentar timeout
- Verificar seletores CSS
- Aguardar mais tempo

### Problema: Sessão não salva

**Solução:**
- Verificar se a pasta `chrome-data` tem permissões corretas
- Não fechar o navegador abruptamente
- Aguardar o programa finalizar corretamente

### Problema: Mensagem não envia

**Verificações:**
- Contato existe e está visível
- Mensagem não está vazia
- Chat está aberto corretamente
- Aguardar mais tempo entre ações

## 📚 Dependências Utilizadas

- **Selenium WebDriver 4.15.0** - Automação web
- **WebDriverManager 5.6.2** - Gerenciamento automático de drivers
- **Apache Commons Lang 3.12.0** - Utilitários de string
- **SLF4J 2.0.9** - Sistema de logging
- **JUnit 5.10.0** - Framework de testes

## 🤝 Contribuições

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -am 'Adicionar nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## ⚠️ Avisos Legais

### Termos de Uso do WhatsApp

- Este projeto é apenas para fins educacionais e de automação pessoal
- Respeite os [Termos de Serviço do WhatsApp](https://www.whatsapp.com/legal/terms-of-service)
- Não use para spam ou atividades maliciosas
- Use com responsabilidade e moderação

### Limitações

- **Rate Limiting**: O WhatsApp pode bloquear contas que enviam muitas mensagens
- **Detecção**: Evite padrões muito repetitivos
- **Atualizações**: O WhatsApp Web pode mudar e quebrar a automação

### Recomendações

- Sempre teste com poucos contatos primeiro
- Use delays adequados entre mensagens
- Monitore sua conta para evitar bloqueios
- Mantenha backups de dados importantes

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📞 Suporte

Para dúvidas, sugestões ou problemas:

1. Abra uma issue no GitHub
2. Verifique a documentação
3. Consulte o troubleshooting

---

**Desenvolvido por Raphael** 🚀

*Última atualização: $(date)*