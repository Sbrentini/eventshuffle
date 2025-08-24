# Eventshuffle 
A simple Spring Boot application used for scheduling. Used for creating an event with suitable dates. 
The dates can be voted for to find what date is most suitable for all participants. 

This application is for educational and demo purposes. When starting the application for the first time 
the database is initialized with some test data: `sql/test_data.sql` 

## Changelog 
### 24.8.2025 - Improvements after submission
#### API documentation 
- Added Swagger/OpenAPI support for easier REST endpoint exploration.

#### README.md update 
- Updated readme accordingly with the new changes. 

## Tech stack
- Java 21 
- Spring boot 3
- MySql 8 (docker)
- Maven (maven wrapper)
- Jpa / Hibernate

## Prerequisites
- Java 21
- Git
- Maven (optional)
- Docker (tested with version 28.3.2)
- Docker compose (tested with version 2.38.2)

## How to run the application 

- clone the repository: 
  - `git clone https://github.com/Sbrentini/eventshuffle.git`
- Start the database connection: 
  - `docker compose up -d`
- build via terminal: 
  - `./mvnw clean install`
- run the application: 
  - `./mvnw spring-boot:run`
- Test on a browser with url: http://localhost:8080/swagger-ui/index.html
  - Swagger UI allows you to view all endpoints, try requests, and see responses. The database will already contain some data for testing. 

## Tests

Tests can be run with:
- `./mvnw test`

### Testing status 
- Service layer: most methods covered with unit tests 
- Controller layer: some isolated test cases 
- Dao layer: missing 
- E2E: 1 complete scenario
- No separate integration testing

## API 
The application exposes REST endpoints to manage events and votes. All endpoints can be explored interactively via Swagger: http://localhost:8080/swagger-ui/index.html

### Main endpoints: 
- List events: `GET /api/v1/event/list` - retrieve all events 
- Create an event: `POST /api/v1/event` - create a new event with dates
- Show an event: `GET /api/v1/event/{id}` - show detail of an event 
- Add votes: `POST /api/v1/event/{id}/vote` - add votes for a specific date on an event
- Show suitable dates: `GET /api/v1/event/{id}/results` - get dates that are suitable for all participants

## Directory structure 

```
eventshuffle/
├── sql/
│   ├── schema.sql
│   └── test_data.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │    └── exercise/
│   │   │        └── eventshuffle/
│   │   │            ├── controller/
│   │   │            ├── dao/
│   │   │            ├── dto/
│   │   │            ├── entity/
│   │   │            ├── exception/
│   │   │            ├── service/
│   │   │            └── EventshuffleApplication.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │    └── exercise/
│       │        └── eventshuffle/
│       │            ├── controller/
│       │            ├── util/
│       │            ├── e2e/
│       │            └── EventshuffleApplicationTests.java
├── docker-compose.yml
├── pom.xml
└── README.md

```


