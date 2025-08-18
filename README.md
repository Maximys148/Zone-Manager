# 📡 Zone-Manager - Система создания зон и реперных точек по Rest-запросам

Система для управления зонами и реперных точек. Проект реализован на Spring Boot с использованием PostgreSQL для хранения данных.

## 🌟 Особенности
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
<div align="left">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Spring_WebSocket-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring WebSocket">
  <img src="https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white" alt="Kafka">
  <img src="https://img.shields.io/badge/Protocol_Buffers-3178C6?style=for-the-badge&logo=protobuf&logoColor=white" alt="Protobuf">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Log4j-1F1F1F?style=for-the-badge&logo=apache&logoColor=white" alt="Log4j">
</div>
