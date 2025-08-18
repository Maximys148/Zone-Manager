package com.stupor.zmr.service;

import com.stupor.zmr.Mapper.Mapper;
import com.stupor.zmr.dto.*;
import com.stupor.zmr.entity.Coordinate;
import com.stupor.zmr.entity.Zone;
import com.stupor.zmr.entity.ZonePolygon;
import com.stupor.zmr.entity.ZoneSector;
import com.stupor.zmr.exception.errors.ConflictException;
import com.stupor.zmr.kafka.KafkaProducer;
import com.stupor.zmr.repository.ZonePolygonRepository;
import com.stupor.zmr.repository.ZoneSectorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final KafkaProducer kafkaProducer;
    private final Mapper mapper;
    private final ZonePolygonRepository zonePolygonRepository;
    private final ZoneSectorRepository zoneSectorRepository;
    private final ZoneCoordinateService zoneCoordinateService;
    private final Logger log = LogManager.getLogger(ZoneService.class);

    public ZonesResponseDto getZones() {
        List<Zone> zones = new ArrayList<>();
        zones.addAll(zonePolygonRepository.findAllWithCoordinates());
        zones.addAll(zoneSectorRepository.findAllWithCenter());
        ZonesResponseDto zonesResponseDto = mapper.convertToResponse(zones);
        log.debug("Зоны успешно получены");
        return zonesResponseDto;
    }

    public ZonePolygonResponseDto createZonePolygon(ZonePolygonCreateDto zonePolygonCreateDto) {
        String equipmentId = zonePolygonCreateDto.getEquipmentId();
        validateEquipmentIdUnique(equipmentId);
        ZonePolygon save = zonePolygonRepository.save(mapper.convertToEntity(zonePolygonCreateDto));
        ZonePolygonResponseDto zonePolygonResponseDto = mapper.convertToResponse(save);
        log.debug("Зона полигон с equipmentId: {} , успешно сохранена", equipmentId);
        kafkaProducer.sendMessage("zone-created", zonePolygonResponseDto);
        return zonePolygonResponseDto;
    }

    public ZoneSectorResponseDto createZoneSector(ZoneSectorCreateDto zoneSectorCreateDto) {
        String equipmentId = zoneSectorCreateDto.getEquipmentId();
        validateEquipmentIdUnique(equipmentId);
        ZoneSector save = zoneSectorRepository.save(mapper.convertToEntity(zoneSectorCreateDto));
        ZoneSectorResponseDto zoneSectorResponseDto = mapper.convertToResponse(save);
        log.debug("Зона сектор с equipmentId: {} , успешно сохранена", equipmentId);
        kafkaProducer.sendMessage("zone-created", zoneSectorResponseDto);
        return zoneSectorResponseDto;

    }

    public ZonePolygonResponseDto updateZonePolygon(ZonePolygonUpdateDto zonePolygonUpdateDto, String zoneId) {
        ZonePolygon existingZone = zonePolygonRepository.findById(zoneId)
                .orElseThrow(() -> new EntityNotFoundException("Зона полигон c id:" + zoneId + " не найдена"));

        // Очищаем старые координаты и добавляем новые
        zoneCoordinateService.clearPolygonCoordinates(zoneId);
        existingZone.setPointsCoordinates(
                zonePolygonUpdateDto.getParams().getPointsCoordinates().stream()
                        .map(dto -> {
                            Coordinate coordinate = new Coordinate();
                            coordinate.setLat(dto.getLat());
                            coordinate.setLng(dto.getLng());
                            return coordinate;
                        })
                        .collect(Collectors.toList())
        );

        ZonePolygon updatedZone = zonePolygonRepository.save(existingZone);
        ZonePolygonResponseDto responseDto = mapper.convertToResponse(updatedZone);

        log.debug("Зона полигон с zoneId: {} успешно обновлена", zoneId);
        kafkaProducer.sendMessage("zone-updated", responseDto);
        return responseDto;
    }

    public ZoneSectorResponseDto updateZoneSector(ZoneSectorUpdateDto zoneSectorUpdateDto, String zoneId) {
        ZoneSector existingZone = zoneSectorRepository.findById(zoneId)
                .orElseThrow(() -> new EntityNotFoundException("Зона сектор c id:" + zoneId + " не найдена"));

        // Обновляем поля
        existingZone.setRadiusInMeters(zoneSectorUpdateDto.getParams().getRadiusInMeters());
        existingZone.setStartAngle(zoneSectorUpdateDto.getParams().getStartAngle());
        existingZone.setEndAngle(zoneSectorUpdateDto.getParams().getEndAngle());

        // Обновляем координаты центра
        if (zoneSectorUpdateDto.getParams().getCenterCoordinates() != null) {
            Coordinate center = existingZone.getCenterCoordinates();
            if (center == null) {
                center = new Coordinate();
            }
            center.setLat(zoneSectorUpdateDto.getParams().getCenterCoordinates().getLat());
            center.setLng(zoneSectorUpdateDto.getParams().getCenterCoordinates().getLng());
            existingZone.setCenterCoordinates(center);
        }

        ZoneSector updatedZone = zoneSectorRepository.save(existingZone);
        ZoneSectorResponseDto responseDto = mapper.convertToResponse(updatedZone);

        log.debug("Зона сектор с zoneId: {} успешно обновлена", zoneId);
        kafkaProducer.sendMessage("zone-updated", responseDto);
        return responseDto;
    }

    public void deleteZone(String zoneId) {
        // Проверяем существование зоны в обоих репозиториях
        boolean zoneExists = zonePolygonRepository.existsById(zoneId)
                || zoneSectorRepository.existsById(zoneId);

        if (!zoneExists) {
            throw new EntityNotFoundException("Зона с ID " + zoneId + " не найдена");
        }

        // Удаляем из обоих репозиториев (если существуют)
        zonePolygonRepository.deleteById(zoneId);
        zoneSectorRepository.deleteById(zoneId);

        log.debug("Зона с ID: {} успешно удалена", zoneId);
        kafkaProducer.sendMessage("zone-deleted", zoneId);
    }

    private void validateEquipmentIdUnique(String equipmentId) {
        if (zonePolygonRepository.existsByEquipmentId(equipmentId) ||
                zoneSectorRepository.existsByEquipmentId(equipmentId)) {
            throw new ConflictException("Зона с equipmentId: " + equipmentId + " уже существует");
        }
    }
}
