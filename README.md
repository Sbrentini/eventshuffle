# Eventshuffle 
A simple Spring Boot application used for scheduling. Used for creating an event with suitable dates. 
The dates can be voted for to find what date is most suitable for all participants. 

This application is for educational and demo purposes. When starting the application for the first time 
the database is initialized with some test data: `sql/test_data.sql` 

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
- Test on browser url: http://localhost:8080/api/v1/event/list 
  - if everything went as expected there should be three events listed, which were set up during database initialization.

## Tests
There are three very basic tests found in src/test/java/exercise/eventshuffle/controller/EventControllerTests.java.

Tests can be run with: 
- `./mvnw test`

## API 
Endpoints can be tested with Postman. 

##### <ins>List events</ins>
**Endpoint**: /api/v1/event/list\
**Request**: GET\
Response Body:
```
{
    "events": [
        {
            "id": 1,
            "name": "Visit Helsinki"
        },
        {
            "id": 2,
            "name": "Birthday party"
        },
        {
            "id": 3,
            "name": "Cat agility event"
        }
    ]
}
```

##### <ins>Create event</ins>
**Endpoint:** /api/v1/event\
**Request**: POST\
Request Body: 
```
{
  "name": "Beach!!!!",
  "dates": [
    "2025-08-01",
    "2025-08-09"
  ]
}
```
Response Body: 
```
{
    "id": 4
}
```

##### <ins>Show an event</ins>
**Endpoint**: /api/v1/event/{id}\
**Request**: GET\
**Parameters**: id, long\
Response Body: 
```
{
    "id": 1,
    "name": "Visit Helsinki",
    "dates": [
        "2025-08-01",
        "2025-08-02",
        "2025-08-03"
    ],
    "votes": [
        {
            "date": "2025-08-01",
            "people": [
                "Anna",
                "Jessie",
                "Jerry",
                "Rafael"
            ]
        },
        {
            "date": "2025-08-02",
            "people": [
                "Anna"
            ]
        },
        {
            "date": "2025-08-03",
            "people": [
                "Anna",
                "Jerry"
            ]
        }
    ]
}
```

##### <ins>Add vote</ins>
**Endpoint**: /api/v1/event/{id}/vote\
**Request**: POST\
**Parameters**: id, long\
Request Body: 
```
{
  "name": "Felix",
  "votes": [
    "2025-08-01",
    "2025-08-02",
    "2025-08-03"
  ]
}
```

Response Body: 

```
{
    "id": 1,
    "name": "Visit Helsinki",
    "dates": [
        "2025-08-01",
        "2025-08-02",
        "2025-08-03"
    ],
    "votes": [
        {
            "date": "2025-08-01",
            "people": [
                "Anna",
                "Jessie",
                "Jerry",
                "Rafael",
                "Felix"
            ]
        },
        {
            "date": "2025-08-02",
            "people": [
                "Anna",
                "Felix"
            ]
        },
        {
            "date": "2025-08-03",
            "people": [
                "Anna",
                "Jerry",
                "Felix"
            ]
        }
    ]
}
```

##### <ins>Show event dates that are suitable for all participants.</ins>
**Endpoint**: /api/v1/event/{id}/results\
**Request**: GET\
**Parameters**: id, long\
Response Body: 
```
{
    "id": 1,
    "name": "Visit Helsinki",
    "suitableDates": [
        {
            "date": "2025-08-01",
            "people": [
                "Anna",
                "Jessie",
                "Jerry",
                "Rafael",
                "Felix"
            ]
        }
    ]
}
```


## Directory structure 

```
eventshuffle/
├── sql/
│   ├── schema.sql
│   └── test_data.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │    └── exercise.eventshuffle/
│   │   │        ├── controller/
│   │   │        ├── dao/
│   │   │        ├── dto/
│   │   │        ├── entity/
│   │   │        ├── exception/
│   │   │        ├── service/
│   │   │        └── EventshuffleApplication.java
│   │   └── resources/
│   └──tests/
│       ├── java/
│       │    └── exercise.eventshuffle/
│       │        ├── controller/
│       │        ├── util/
│       │        └── EventshuffleApplicationTests.java
└── docker-compose.yml
└── pom.xml
└── README.md
```


