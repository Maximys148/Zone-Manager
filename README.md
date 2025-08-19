# 🗺️ Zone-Manager - Система создания зон и реперных точек

Система для управления зонами и реперными точками. Проект реализован на Spring Boot с использованием PostgreSQL для хранения данных.

## 🌟 Особенности
- **CRUD операции** для зон (POLYGON/SECTOR) и реперных точек
- **Асинхронная обработка** событий через Kafka
- **Масштабируемая архитектура** микросервисов
- **Логирование** ключевых операций

### ReferencePointController
`GET /api/shtilFarvater/dashboard_post` - Получение реперной точки

**Тело ответа:**
```json
{
  "ip": "192.168.1.100",
  "latitude": "59.93428",
  "longitude": "30.33510",
  "port": "8080",
  "public_id": "FARV-2023-001",
  "radius": "5000"
}
```

!!! ВАЖНО
- Добавить файл application.yml в src/main/resources/ в нём ты указываешь ip и порты.
Ниже приведу пример файла
```
server:
  port: 9898
  address: ${IP:192.168.33.33}

spring:
  application:
    name: zones
  output:
    ansi:
      enabled: ALWAYS
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://${DB_IP:192.168.33.33}:9999/postgres
    username: postgres
    password: "186Kdc9899K"
  kafka:
    producer:
      bootstrap-servers: ${KAFKA_URL:192.168.11.11:8888}
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
    ip-address: ${server.address}
    instance-id: ${server.address}:${server.port}
  client:
    service-url:
      defaultZone: http://192.168.1.1:2222/eureka/
    register-with-eureka: true
    fetch-registry: true


 # kafka:
  #  bootstrap-servers: ${KAFKA_URL:localhost:1111}
   # producer:
    #  key-serializer: org.apache.kafka.common.serialization.StringSerializer
     # value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
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
