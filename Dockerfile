# Этап сборки
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# 1. Копируем весь проект
WORKDIR /workspace
COPY . .

# 2. Сначала устанавливаем родительский POM (из корня проекта)
WORKDIR /workspace
RUN mvn install -N -DskipTests

# 3. Переходим в директорию модуля zone-manager (обратите внимание на имя папки)
WORKDIR /workspace/zone_manager
RUN mvn install -DskipTests

# Этап запуска
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/zone_manager/target/*.jar app.jar
EXPOSE 9898
ENTRYPOINT ["java", "-jar", "app.jar"]