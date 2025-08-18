# 📡 Zone-Manager - Система создания зон и реперных точек

Система для управления зонами и реперных точек. Проект реализован на Spring Boot с использованием PostgreSQL для хранения данных.

## 🌟 Особенности
- **CRUD операции** для зон (POLYGON/SECTOR) и реперных точек
- **Асинхронная обработка** событий через Kafka
- **Масштабируемая архитектура** микросервисов
- **Логирование** ключевых операций

## 📦 Основные компоненты

### `config/`
- `KafkaConfig.java` - Настройки продюсеров Kafka

### `controllers/`
- `ReferencePointController` - Отвечает за запрос CRUD запросы к реперной точке
- `ZoneController` - Отвечает за запрос CRUD запросы к зонам

### `dto/`
- Различные DTO: координаты, реперная точка, зоны типа (POLYGON и SECTOR) и классы отетов на CRUD запросы для предыидкщих DTO

### `entity/`
- Различные Entity: координаты, реперная точка, зона POLYGON, зона SECTOR

### `enums/`
- Типы зон

### `exceptions/`
- `DetectorException` - Обрабатывает все ошибки приложения

### `kafka/`
- `KafkaProducer` - Отправляет сообщение в Kafka (название топика указывается в параметре метода)

### `mapper/`
- Преобразует DTO в entity или ответ (Response на CRUD запрос)

### `services/`
- `MessageService` - Обрабатывает сигналы и отправляет их в Kafka через KafkaProducer
- `MoveService` - Управляет передвижением устройства (Фарватора), изменяя его координаты

### `repository/`
- Репозитории для баз данных

### `ZoneManagerApplication`
- Главный класс приложения, который запускает его

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
