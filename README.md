#  Finance Education API

Sistema completo de Educação Financeira desenvolvido com **Spring Boot + JWT + PostgreSQL**.

Este projeto permite controle financeiro mensal com metas, limite diário, insights e acompanhamento completo.

---

#  Tecnologias

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* PostgreSQL
* JPA / Hibernate
* Maven

---

#  Autenticação

O sistema utiliza autenticação JWT.

Endpoints públicos:

POST /auth/register
POST /auth/login

Todas as outras rotas requerem:

Authorization: Bearer TOKEN

---

# Funcionalidades

* Cadastro de usuário
* Login com JWT
* Dashboard financeiro
* Controle de renda
* Controle de gastos
* Meta de economia
* Limite diário
* Gasto do dia
* Restante do dia
* Progresso mensal
* Insight financeiro
* Categorias
* Transações
* Sistema por mês financeiro

---

#  Endpoints

## Auth

POST /auth/register
POST /auth/login

## Month

POST /months
GET /months/{id}/summary

## Transactions

POST /transactions
GET /months/{id}/transactions

## Categories

GET /categories
POST /categories

---

#  Banco de Dados

PostgreSQL

Configuração:

application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db
spring.datasource.username=postgres
spring.datasource.password=1234

---

#  JWT

Configuração:

jwt.secret=finance-secret-key
jwt.expiration=86400000

---

#  Como rodar

1. Criar banco PostgreSQL

finance_db

2. Rodar projeto

```bash
mvn spring-boot:run
```

Servidor:

http://localhost:8080

---

# Desenvolvido por

Bruna Costa
Sistema Full Stack de Educação Financeira
