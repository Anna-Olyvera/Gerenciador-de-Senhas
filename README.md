# 🔐 Gerenciador de Senhas

## 📝 Descrição

Este projeto tem como objetivo desenvolver uma aplicação em Java que permita aos usuários **armazenar e gerenciar senhas de maneira segura**, com recursos modernos de autenticação e segurança da informação.

A aplicação foi construída utilizando o padrão arquitetural **MVC (Model-View-Controller)**, que separa as responsabilidades entre dados (Model), interface gráfica (View) e regras de negócio (Controller), garantindo um sistema modular, de fácil manutenção e escalável.

---

## 💻 Tecnologias Utilizadas

- **Java**
- **Firebase** (como banco de dados)
- **Swing** (interface gráfica)
- **Jakarta Mail** (envio de e-mails)
- **Maven** (gerenciador de dependências)
- **PBKDF2WithHmacSHA256** (criptografia de senhas)
- **SpotBugs** e **SonarQube** (análise de qualidade e segurança de código)
- **Apache HttpClient** — Realiza requisições HTTP para verificação de e-mails vazados.
- **Gson** — Biblioteca do Google para manipulação de JSON.
- **API Pwned Passwords (Have I Been Pwned)** — Verifica se uma senha foi exposta em vazamentos.
- **API HackCheck (WovenTeams)** — Verifica se um e-mail foi comprometido em vazamentos de dados.

---

## 🚀 Funcionalidades

- Cadastro de usuários
- Armazenamento seguro de senhas de serviços diversos
- **Criptografia de senhas** com PBKDF2 + HMAC SHA-256 e salt aleatório
- **Autenticação em dois fatores** com envio de token por e-mail
- **Token com validade de 10 minutos** (é necessário reenviar após expiração)
- Bloqueio de usuário por **30 minutos após 3 tentativas falhas**
- Geração de **senhas fortes e aleatórias**
- **Verificação de vazamentos de senhas** usando APIs externas
- Interface gráfica simples e funcional
- Compilação em arquivo `.exe` para uso sem necessidade de IDE

---

## 🛠️ Instalação e Execução

Para utilizar o Gerenciador de Senhas, basta seguir os passos abaixo:

1. **Baixe o executável**:
   - [`gerenciasenha.exe`](#)

2. **Execute o arquivo** com um duplo clique.
   - Não é necessário ter uma IDE como Eclipse ou IntelliJ instalada.
   - O sistema está pronto para uso local após a execução.

---

## 🧪 Testes

A aplicação foi testada com:

- **JUnit 5** – Framework de testes unitários utilizado para validar o comportamento de métodos essenciais do sistema
- **Mockito** – Utilizado para simulação (mock) de dependências e verificação de interações, garantindo testes isolados e confiáveis
- **SpotBugs** – Identificação de bugs e vulnerabilidades comuns em Java
- **SonarQube** – Avaliação da qualidade do código, cobertura de testes e segurança

Essas ferramentas contribuíram para manter o projeto com **alto padrão de qualidade, segurança e desempenho**, garantindo que os componentes críticos funcionem corretamente e que o código esteja livre de falhas comuns.

---

## 👩‍💻 Autor

**Ana Beatriz Gonçalo de Oliveira**  
📧 anab.goncalo@gmail.com

---