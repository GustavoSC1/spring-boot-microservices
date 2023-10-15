# Spring Boot Microservices

Projeto desenvolvido durante o curso **Spring Boot Microservices** do canal **Programming Techie**.

O projeto consiste em uma aplicação simples de loja online desenvolvido usando padrões de arquitetura de microserviços e, principalmente, usando o **Spring Boot** e as mais recentes tecnologias **Spring Cloud**.

Padrões arquitetônicos interessantes e importantes que foram abordados:

- Service discovery 
- Centralized configuration
- Distributed tracing
- Event driven architecture
- Centralized logging 
- Circuit breaker
- Secure microservice using keycloak
 
Os diferentes serviços que foram construidos nesse projeto:

- **Product Service -** Criar e visualizar produtos, atua como catálogo de produtos.
- **Order Service -** Pode encomendar produtos.
- **Inventory Service -** Verifica se o produto está em estoque ou não.
- **Notification Service -** Envia notificações após o pedido ser feito.
- Order Service, Inventory Service e Notification Service irão interagir entre si.
- Comunicação Síncrona e Assíncrona.

## Arquitetura

## Tecnologias utilizadas

- [Java 17](https://www.oracle.com/java/)
- [MySQL](https://www.mysql.com/)
- [MongoDB](https://www.mongodb.com/pt-br)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Resilience4j](https://resilience4j.readme.io/docs)
- [Zipkin](https://zipkin.io/)
- [Maven](https://maven.apache.org/)
- [Keycloak](https://www.keycloak.org/)
- [Kafka](https://kafka.apache.org/)
- [Docker](https://www.docker.com/)

## Como executar a aplicação usando Docker

1. Execute `mvn clean package -DskipTests` para construir as aplicações e gerar a imagem docker localmente.
2. Execute `docker-compose up -d` para iniciar as aplicações.

## Como executar a aplicação sem Docker

1. Execute `mvn clean verify -DskipTests` entrando em cada pasta para construir as aplicações.
2. Depois disso execute `mvn spring-boot:run` entrando em cada pasta para iniciar as aplicações.

## Autor

Gustavo da Silva Cruz

https://www.linkedin.com/in/gustavo-silva-cruz-20b128bb/
