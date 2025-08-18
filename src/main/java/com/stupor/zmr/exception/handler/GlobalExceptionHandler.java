package com.stupor.zmr.exception.handler;

import com.stupor.zmr.exception.errors.ConflictException;
import com.stupor.zmr.exception.errors.ExternalServiceException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Ресурс не найден: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Ошибка в запросе: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        log.error("Конфликт. Ресурс уже существует: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException ex) {
        log.error("Недействительное сообщение от внешнего сервиса: {}", ex.getMessage());
        if (ex.isTimeout()) {
            return buildErrorResponse(HttpStatus.GATEWAY_TIMEOUT, ex.getMessage());
        }
        return buildErrorResponse(HttpStatus.BAD_GATEWAY, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Внутренняя ошибка сервера: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String reason) {
        ErrorResponse errorResponse = new ErrorResponse(
                getDefaultMessage(status),
                reason,
                LocalDateTime.now().format(TIMESTAMP_FORMATTER),
                status.name()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    private String getDefaultMessage(HttpStatus status) {
        return switch (status) {
            case BAD_REQUEST -> "Ошибка в запросе.";
            case UNAUTHORIZED -> "Пользователь не авторизован.";
            case FORBIDDEN -> "Пользователь не имеет прав доступа к ресурсу.";
            case NOT_FOUND -> "Ресурс не найден.";
            case CONFLICT -> "Конфликт. Ресурс уже существует.";
            case INTERNAL_SERVER_ERROR -> "Внутренняя ошибка сервера.";
            case BAD_GATEWAY -> "Недействительное сообщение от внешнего сервиса.";
            case GATEWAY_TIMEOUT -> "Внешний сервис не отвечает.";
            default -> "Произошла ошибка.";
        };
    }

    public record ErrorResponse(String message, String reason, String timestamp, String status) {
    }
}