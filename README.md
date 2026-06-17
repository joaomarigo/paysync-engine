# PaySync Engine ⚙️💳

Uma arquitetura de microsserviços distribuída focada em alta disponibilidade e resiliência para ingestão e conciliação de eventos financeiros.

## 🎯 O Desafio
Em sistemas de pagamento, a perda de um único evento de *webhook* (devido a falhas de rede ou indisponibilidade do banco de dados) resulta em falhas no fluxo de caixa. Este projeto resolve esse problema desacoplando o recebimento da transação do seu processamento final, garantindo que nenhum dado financeiro seja perdido.

## 🏗️ Arquitetura do Sistema
O ecossistema é composto por dois microsserviços Spring Boot que se comunicam de forma assíncrona:

1. **Payment Ingestion API (Porta 8080):** Atua como a porta de entrada. Recebe os *webhooks* dos gateways de pagamento, valida o *payload* e posta a mensagem imediatamente em uma fila, devolvendo uma resposta ultra-rápida (Status 200 OK) para o gateway não sofrer *timeout*.
2. **Reconciliation Service (Porta 8081):** O serviço trabalhador (*Consumer*). Escuta a fila ativamente, consome as transações e aplica as regras de negócio antes de persistir os dados permanentemente.

## 🛡️ Destaques de Segurança e Engenharia
* **Mensageria com RabbitMQ:** Implementado para atuar como *buffer*. Se o banco de dados cair, as requisições não falham; elas se acumulam de forma segura na fila até a infraestrutura voltar.
* **Idempotência Transacional:** Mecanismo de defesa no consumidor que verifica o `transaction_id`. Garante que requisições duplicadas enviadas por erro do gateway externo não gerem cobranças ou registros duplicados no banco de dados.
* **Batch Processing (Cron Job):** Rotina automatizada que audita as tabelas diariamente, filtra transações rejeitadas e consolida o faturamento real sem necessidade de intervenção humana.

## 🛠️ Stack Tecnológica
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Mensageria:** RabbitMQ
* **Banco de Dados:** MySQL
* **Infraestrutura:** Docker & Docker Compose
* **ORM:** Spring Data JPA / Hibernate

## 🚀 Como Executar Localmente

1. Suba a infraestrutura (MySQL e RabbitMQ):
   ```bash
   docker-compose up -d