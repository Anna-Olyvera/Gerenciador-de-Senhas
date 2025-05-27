# 🔐 Gerenciador-de-Senhas
Este é um sistema de gerenciamento de senhas desenvolvido em Java, utilizando Maven para gerenciamento de dependências, Firebase como banco de dados e Swing para a construção da interface gráfica. O projeto segue o padrão de arquitetura MVC (Model-View-Controller) para melhor organização do código e separação de responsabilidades.

## 📌 Funcionalidades
- Cadastro e autenticação de usuários com chave mestra;
- Verificação em duas etapas (2FA) com envio de token por e-mail;
- Armazenamento seguro de credenciais (nome do serviço e senha);
- Visualização das senhas armazenadas;
- Criptografia das senhas antes do armazenamento;
- Interface gráfica amigável com Java Swing;
- Integração com Firebase Realtime Database.

## 🧰 Tecnologias Utilizadas
- Java 24
- Maven
- Firebase Realtime Database
- Java Swing
- Arquitetura MVC
- JavaMail API (para envio de tokens por e-mail)
- Firebase Admin SDK

## ⚙️ Como Executar

### Pré-requisitos
- Java 24
- Maven instalado

### Passos para rodar o projeto
1. Clone este repositório;
git clone https://github.com/seu-usuario/gerenciador-senhas.git
cd gerenciador-senhas

2. Coloque o arquivo serviceAccountKey.json na pasta resources/;
mvn clean install
mvn exec:java
Ou, execute pela sua IDE favorita (Eclipse, IntelliJ, VS Code).

## 🔐 Segurança
- As senhas dos serviços são criptografadas localmente antes de serem enviadas ao Firebase.
- O login é protegido por uma chave mestra
- O sistema implementa verificação em duas etapas com envio de token único por e-mail ou SMS.

## 🧭 Padrão MVC
O projeto segue o padrão Model-View-Controller, facilitando a manutenção e escalabilidade do código:
- Model: gerencia os dados e integra com o Firebase;
- View: interface com o usuário utilizando Java Swing;
- Controller: coordena a lógica entre a View e o Model.

## 👨‍💻 Desenvolvedores
Ana Oliveria