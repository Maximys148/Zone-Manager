package com.stupor.zmr.service;

import com.stupor.zmr.dto.CoordinateDto;
import com.stupor.zmr.entity.Coordinate;
import com.stupor.zmr.entity.ZonePolygon;
import com.stupor.zmr.entity.ZoneSector;
import com.stupor.zmr.repository.CoordinateRepository;
import com.stupor.zmr.repository.ZonePolygonRepository;
import com.stupor.zmr.repository.ZoneSectorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneCoordinateService {
    private final CoordinateRepository coordinateRepository;
    private final ZoneSectorRepository zoneSectorRepository;
    private final ZonePolygonRepository zonePolygonRepository;

    @Transactional
    public void clearPolygonCoordinates(String polygonId) {
        // 1. Удаляем связи из join-таблицы
        coordinateRepository.deletePolygonCoordinateLinks(polygonId);
        // 2. Удаляем "осиротевшие" координаты
        List<Coordinate> orphanedCoordinates = coordinateRepository.findOrphanedCoordinates();
        coordinateRepository.deleteAll(orphanedCoordinates);
    }

    @Transactional
    public void clearSectorCoordinates(String polygonId) {
        // 1. Удаляем связи из join-таблицы
        coordinateRepository.detachSectorCenterCoordinate(polygonId);
        // 2. Удаляем "осиротевшие" координаты
        List<Coordinate> orphanedCoordinates = coordinateRepository.findOrphanedCoordinates();
        coordinateRepository.deleteAll(orphanedCoordinates);
    }


    @Transactional
    public void updatePolygonCoordinates(String zoneId, List<CoordinateDto> coordinates) {
        clearPolygonCoordinates(zoneId);

        ZonePolygon zone = zonePolygonRepository.findById(zoneId)
                .orElseThrow(() -> new EntityNotFoundException("Zone not found"));

        List<Coordinate> coordinateEntities = coordinates.stream()
                .map(dto -> {
                    Coordinate coordinate = new Coordinate();
                    coordinate.setLat(dto.getLat());
                    coordinate.setLng(dto.getLng());
                    return coordinate;
                })
                .collect(Collectors.toList());

        zone.setPointsCoordinates(coordinateEntities);
        zonePolygonRepository.save(zone);
    }

    @Transactional
    public void updateSectorCenterCoordinate(String zoneId, CoordinateDto centerCoordinate) {
        clearSectorCoordinates(zoneId);

        ZoneSector zone = zoneSectorRepository.findById(zoneId)
                .orElseThrow(() -> new EntityNotFoundException("Zone not found"));

        Coordinate center = zone.getCenterCoordinates();
        if (center == null) {
            center = new Coordinate();
        }
        center.setLat(centerCoordinate.getLat());
        center.setLng(centerCoordinate.getLng());

        zone.setCenterCoordinates(center);
        zoneSectorRepository.save(zone);
    }
    
    private void deleteIfOrphaned(Long coordinateId) {
        Coordinate coord = coordinateRepository.findById(coordinateId).orElse(null);
        if (coord != null && coordinateRepository.findOrphanedCoordinates().contains(coord)) {
            coordinateRepository.delete(coord);
        }
    }
}