# 🗺️ Zone-Manager - Система создания зон и реперных точек

Система для управления зонами и реперными точками. Проект реализован на Spring Boot с использованием Apache Kafka для передачи данных. Клиент делает Rest-запрос, система обрабатвает его и передаёт в нужный топик кафки DTO, далее кафка пересылает DTO другому микросервису, который работает с полученными данными и переносит их в БД 

## 🌟 Особенности
- **CRUD операции** для зон (POLYGON/SECTOR) и реперных точек
- **Асинхронная обработка** событий через Kafka
- **Масштабируемая архитектура** микросервисов
- **Логирование** ключевых операций

### ReferencePointController

`GET /api/zones/refpoint` - Получение реперной точки

**Тело ответа:**
```json
{
  "id": "192.168.1.100",
  "lat": 59.93428,
  "lng": 30.33510
}
```
`POST /api/zones/refpoint` - Создание реперной точки

**Тело запроса:**
```json
{
  "lat": 59.93428,
  "lng": 30.33510
}
```

**Тело ответа:**
```json
{
  "lat": 59.93428,
  "lng": 30.33510
}
```
`PATCH /api/zones/refpoint` - Обновление реперной точки

**Тело запроса:**
```json
{
  "lat": 59.93428,
  "lng": 30.33510
}
```

**Тело ответа:**
```json
{
  "lat": 59.93428,
  "lng": 30.33510
}
```
`DELETE /api/zones/refpoint` - Удаление реперной точки

### ReferencePointController

`GET /api/zones` - Создание реперной точки

**Тело ответа:**
```json
{
  "zones": [
    {
      "type": "POLYGON",
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "equipmentId": "EQUIP-12345",
      "pointsCoordinates": [
        {"lat": 59.93428, "lng": 30.33510},
        {"lat": 59.93500, "lng": 30.33600},
        {"lat": 59.93350, "lng": 30.33400},
        {"lat": 59.93428, "lng": 30.33510}
      ]
    },
    {
      "type": "SECTOR",
      "id": "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
      "equipmentId": "EQUIP-67890",
      "centerCoordinates": {"lat": 59.93900, "lng": 30.34000},
      "radiusInMeters": 500,
      "startAngle": 45,
      "endAngle": 135
    }
  ]
}
```
`POST /api/zones/polygon` - Создание зоны полигон

**Тело запроса:**
```json
{
  "equipmentId": "EQUIP-12345",
  "params": {
    "pointsCoordinates": [
      {
        "lat": 59.93428,
        "lng": 30.33510
      },
      {
        "lat": 59.93500,
        "lng": 30.33600
      },
      {
        "lat": 59.93350,
        "lng": 30.33400
      }
    ]
  }
}
```

**Тело ответа:**
```json
{
  "equipmentId": "EQUIP-12345",
  "zoneType": "POLYGON"
  "params": {
    "pointsCoordinates": [
      {
        "lat": 59.93428,
        "lng": 30.33510
      },
      {
        "lat": 59.93500,
        "lng": 30.33600
      },
      {
        "lat": 59.93350,
        "lng": 30.33400
      }
    ]
  }
}
```

`POST /api/zones/sector` - Создание зоны сектор

**Тело запроса:**
```json
{
  "equipmentId": "EQUIP-12345",
  "params": {
    "centerCoordinates": {
      "lat": 59.93428,
      "lng": 30.33510
    },
    "radiusInMeters": 500,
    "startAngle": 45,
    "endAngle": 135
  }
}
```

**Тело ответа:**
```json
{
  "equipmentId": "EQUIP-12345",
  "zoneType": "CIRCLE_SECTOR",
  "params": {
    "centerCoordinates": {
      "lat": 59.93428,
      "lng": 30.33510
    },
    "radiusInMeters": 500,
    "startAngle": 45,
    "endAngle": 135
  }
}
```

`PATCH /api/zones/polygon/{zoneId}` - Обновление зоны полигон, параметр String (id зоны)

**Тело запроса:**
```json
{
  "params": {
    "pointsCoordinates": [
      {
        "lat": 59.93428,
        "lng": 30.33510
      },
      {
        "lat": 59.93500,
        "lng": 30.33600
      },
      {
        "lat": 59.93350,
        "lng": 30.33400
      }
    ]
  }
}
```

**Тело ответа:**
```json
{
  "equipmentId": "EQUIP-12345",
  "zoneType": "POLYGON"
  "params": {
    "pointsCoordinates": [
      {
        "lat": 59.93428,
        "lng": 30.33510
      },
      {
        "lat": 59.93500,
        "lng": 30.33600
      },
      {
        "lat": 59.93350,
        "lng": 30.33400
      }
    ]
  }
}
```

`PATCH /api/zones/sector/{zoneId}` - Обновление зоны сектор, параметр String (id зоны)


**Тело запроса:**
```json
{
  "params": {
    "centerCoordinates": {
      "lat": 59.93428,
      "lng": 30.33510
    },
    "radiusInMeters": 500,
    "startAngle": 45,
    "endAngle": 135
  }
}
```

**Тело ответа:**
```json
{
  "equipmentId": "EQUIP-12345",
  "zoneType": "SECTOR",
  "params": {
    "centerCoordinates": {
      "lat": 59.93428,
      "lng": 30.33510
    },
    "radiusInMeters": 500,
    "startAngle": 45,
    "endAngle": 135
  }
}
```

`DELETE /api/zone/{zoneId}` - удаление зоны, параметр String (id зоны)


!!! ВАЖНО
- Добавить файл application.yml в src/main/resources/ в нём ты указываешь ip и порты.
Ниже приведу пример файла
```
server:
  port: 8888
  address: 1.1.1.1

spring:
  application:
    name: zones
  output:
    ansi:
      enabled: ALWAYS
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://${DB_IP:192.168.0.0}:2222/postgres
    username: postgres
    password: "186Kdc9899K"
  kafka:
    producer:
      bootstrap-servers: ${KAFKA_URL:192.168.0.0}:2222
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    properties:
      spring.json.add.type.headers: false
      spring.json.type.mapping:
        zonePolygon:com.stupor.zmr.dto.ZonePolygonResponseDto,
        zoneSector:com.stupor.zmr.dto.ZoneSectorResponseDto
  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        jdbc:
          lob:
            non_contextual_creation: true
eureka:
  instance:
    prefer-ip-address: true
    ip-address: ${IP:192.168.2.2}
    instance-id: ${IP:192.168.2.2}:${server.port}
  client:
    service-url:
      defaultZone: http://${EUREKA_IP:192.168.2.3}:5555/eureka/
    register-with-eureka: true
    fetch-registry: true
```
## 🛠️ Технологический стек
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Web](https://img.shields.io/badge/Spring_Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![REST](https://img.shields.io/badge/REST-FF6C37?style=for-the-badge&logo=rest&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Log4j](https://img.shields.io/badge/Log4j-1F1F1F?style=for-the-badge&logo=apache&logoColor=white)
