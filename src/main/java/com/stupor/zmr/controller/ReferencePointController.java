package com.stupor.zmr.controller;

import com.stupor.zmr.dto.RefPointDto;
import com.stupor.zmr.dto.RefPointResponseDto;
import com.stupor.zmr.entity.RefPoint;
import com.stupor.zmr.service.RefPointService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones/refpoint")
@RequiredArgsConstructor
public class ReferencePointController {
    private final RefPointService referencePointService;
    private final Logger log = LogManager.getLogger(ReferencePointController.class);

    @GetMapping
    public ResponseEntity<?> getReferencePoint() {
        log.info("API GET /api/refpoint | Запрос на получение реперной точки");
        RefPoint referencePoint = referencePointService.getReferencePoint();
        return ResponseEntity.status(200).body(referencePoint);
    }

    @PostMapping
    public ResponseEntity<?> createReferencePoint(@RequestBody RefPointDto refPointDto) {
        log.info("API POST /api/refpoint | Запрос на создание реперной точки, тело запроса: {}", refPointDto);
        RefPointResponseDto referencePoint = referencePointService.createReferencePoint(refPointDto);
        return ResponseEntity.status(201).body(referencePoint);
    }

    @PatchMapping
    public ResponseEntity<?> updateReferencePoint(@RequestBody RefPointDto refPointDto) {
        log.info("API PATCH /api/refpoint | Запрос на обновление реперной точки, тело запроса: {}", refPointDto);
        RefPointResponseDto refPoint = referencePointService.updateReferencePoint(refPointDto);
        return ResponseEntity.status(200).body(refPoint);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReferencePoint() {
        log.info("API DELETE /api/refpoint | Запрос на удаление реперной точки");
        referencePointService.deleteReferencePoint();
        return ResponseEntity.status(204).build();
    }
}
