# 🏦 Sistema Bancário – Projeto Bank Accenture

## 🧾 Visão Geral

Este projeto é um **sistema bancário online** desenvolvido como exercício acadêmico avançado em Java e Spring Boot, com frontend em React.  
Ele implementa operações essenciais de um banco, incluindo:

- Cadastro de **Clientes**, **Agências** e **Contas Correntes**  
- 💰 Operações de **Depósito**, **Saque** e **Transferência**  
- 📄 Consulta de **Extratos** e **Saldo**  
- 🔐 Validações de regras de negócio:
  - Saldo insuficiente
  - Conta inexistente
  - Valor inválido  
- 🧪 Testes unitários com **JUnit 5** e **Mockito**  
- 📝 Logs detalhados de operações via **SLF4J**  

O backend utiliza **H2 Database** em memória/local para facilitar a execução sem precisar de banco externo.  
O frontend é uma aplicação **React**, consumindo a API Spring Boot.

---

## ⚙️ Tecnologias Utilizadas

### Backend
Java, Spring Boot, Spring Data JPA, H2 Database, JUnit + Mockito + SLF4J

### Frontend
React, Axios, CSS, JS

---

## 🚀 Requisitos

Antes de executar o projeto:

- Java JDK 21 instalado  
- Maven configurado no PATH  
- Node.js + npm instalados  
- IDE recomendada (IntelliJ, Eclipse STS)  
   
---

## 🚀 Requisitos

Antes de executar o projeto:

- Java JDK 21 instalado  
- Maven configurado no PATH  
- Node.js + npm instalados  
- IDE recomendada (IntelliJ, Eclipse STS)  

---

## ▶️ Execução do Backend

No terminal, dentro da pasta backend/:

bash
mvn clean install
mvn spring-boot:run

* API disponível em: http://localhost:8080
* Console H2 em: http://localhost:8080/h2-console

---

## ▶️ Configuração padrão H2:

*JDBC URL: jdbc:h2:mem:banco
*User: sa
*Password: (vazio)

---

### ▶️ Execução do Frontend

No terminal, dentro da pasta frontend/:

*npm install*
*npm start*

Frontend disponível em: http://localhost:3000

---

### 🧪 Testes Unitários

Cobrem:
*Depósito, saque e transferência
*Regras de negócio e validações
*Exceções: SaldoInsuficienteException, ContaNaoEncontradaException, ValorInvalidoException

Cobertura atual: ~100% para operações críticas.

---

**Desenvolvedores: Enzo Martins Palumbo, Maria Luíza Araújo, Mariana Vitoria Batista, Rafael Victor Rodrigues, Ryan Pereira Lima da Silva.** 

---
