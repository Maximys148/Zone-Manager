package com.stupor.zmr.controller;

import com.stupor.zmr.dto.*;
import com.stupor.zmr.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;
    private final Logger log = LogManager.getLogger(ZoneController.class);

    @GetMapping()
    public ResponseEntity<?> getZones() {
        log.info("API GET /api/zones | Запрос на получение всех зон");
        ZonesResponseDto zonesResponseDto = zoneService.getZones();
        return ResponseEntity.status(200).body(zonesResponseDto);
    }

    @PostMapping("/polygon")
    public ResponseEntity<?> createPolygonZone(@RequestBody ZonePolygonCreateDto zonePolygonCreateDto) {
        log.info("API Post /api/zones/polygon |  Запрос на создание зоны типа: POLYGON, тело запроса: {}", zonePolygonCreateDto);
        ZonePolygonResponseDto zonePolygonResponseDto = zoneService.createZonePolygon(zonePolygonCreateDto);
        return ResponseEntity.status(201).body(zonePolygonResponseDto);
    }

    @PostMapping("/sector")
    private ResponseEntity<?> createSectorZone(@RequestBody ZoneSectorCreateDto zoneSectorCreateDto) {
        log.info("API Post /api/zones/sector | Запрос на создание зоны типа: CIRCLE_SECTOR, тело запроса: {}", zoneSectorCreateDto);
        ZoneSectorResponseDto zoneSectorResponseDto = zoneService.createZoneSector(zoneSectorCreateDto);
        return ResponseEntity.status(201).body(zoneSectorResponseDto);
    }

    @PatchMapping("/polygon/{zoneId}")
    private ResponseEntity<?> updatePolygonZone(@RequestBody ZonePolygonUpdateDto zonePolygonUpdateDto, @PathVariable String zoneId) {
        log.info("API Patch /api/zones/polygon/{zoneId} | Запрос на обновление зоны типа: POLYGON, id зоны: {}, тело запроса: {}", zoneId, zonePolygonUpdateDto);
        ZonePolygonResponseDto zonePolygonResponseDto = zoneService.updateZonePolygon(zonePolygonUpdateDto, zoneId);
        return ResponseEntity.status(201).body(zonePolygonResponseDto);
    }

    @PatchMapping("/sector/{zoneId}")
    private ResponseEntity<?> updateSectorZone(@RequestBody ZoneSectorUpdateDto zoneSectorUpdateDto, @PathVariable String zoneId) {
        log.info("API Patch /api/zones/sector/{zoneId} | Запрос на обновление зоны типа: CIRCLE_SECTOR, id зоны: {}, тело запроса: {}", zoneId, zoneSectorUpdateDto);
        ZoneSectorResponseDto zoneSectorResponseDto = zoneService.updateZoneSector(zoneSectorUpdateDto, zoneId);
        return ResponseEntity.status(200).body(zoneSectorResponseDto);
    }

    @DeleteMapping("/{zoneId}")
    private ResponseEntity<?> deleteZone(@PathVariable String zoneId) {
        log.info("API Delete /api/zones/{zoneId} | Запрос на удаление зоны по id: {}", zoneId);
        zoneService.deleteZone(zoneId);
        return ResponseEntity.status(204).body("Зона с id: " + zoneId + "успешно удалена");
    }
}