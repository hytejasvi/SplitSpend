# SplitSpend
Micro-service expense-splitter that calculates the **minimum number of transactions** needed to settle group debts.

&gt; Early-stage – built in public while mastering DSA + Java 17 + Spring Boot 3 + Kafka.

## Tech Stack (MVP)
* Java 17
* Spring Boot 3.2 – multi-module Maven build
* MySQL 8
* Testcontainers (integration tests)
* Docker Compose
* Kafka (phase-2 for real-time settlement events)

## Modules
| Service         | Responsibility                          | Port |
|----------------|-----------------------------------------|------|
| auth-service    | JWT sign-up / login                     | 8081 |
| group-service   | Create group, add/remove members        | 8082 |
| expense-service | CRUD expense + splits                   | 8083 |
| settlement-service | Min-transaction algorithm & ledger   | 8084 |

## Local Quick-start
```bash
docker-compose up -d   # MySQL + phpMyAdmin
mvn clean install      # root folder
mvn spring-boot:run -pl app/auth-service
```

## API docs (Swagger) at
   http://localhost:8081/swagger-ui.html **(once controllers are implemented).

## Road-map
[ ] Week-1  : Auth + Group CRUD
[ ] Week-2  : Expense & split engine
[ ] Week-3  : Settlement algo (greedy + DFS)
[ ] Week-4  : Kafka events, CI/CD, Railway deploy
