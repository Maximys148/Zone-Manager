package com.stupor.zmr.Mapper;

import com.stupor.zmr.dto.CoordinateDto;
import com.stupor.zmr.entity.*;
import com.stupor.zmr.enums.ZoneType;
import org.springframework.stereotype.Component;
import com.stupor.zmr.dto.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public ZonePolygon convertToEntity(ZonePolygonCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dto отсутсвует");
        }
        if (dto.getEquipmentId() == null) {
            throw new IllegalArgumentException("ID оборудования обязательно");
        }
        if (dto.getParams() == null) {
            throw new IllegalArgumentException("Параметры зоны обязательны");
        }

        ZonePolygon zonePolygon = new ZonePolygon();
        ZonePolygonCreateDto.ZoneParams params = dto.getParams();
        zonePolygon.setEquipmentId(dto.getEquipmentId());
        zonePolygon.setZoneType(ZoneType.POLYGON);

        if (params != null && params.getPointsCoordinates() != null) {
            List<com.stupor.zmr.entity.Coordinate> coords = params.getPointsCoordinates().stream()
                    .map(coordDto -> {
                        Coordinate coord = new Coordinate();
                        coord.setLat(coordDto.getLat());
                        coord.setLng(coordDto.getLng());
                        return coord;
                    })
                    .collect(Collectors.toList());
            zonePolygon.setPointsCoordinates(coords);
        }
        return zonePolygon;
    }

    public ZoneSector convertToEntity(ZoneSectorCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dto отсутсвует");
        }
        if (dto.getEquipmentId() == null) {
            throw new IllegalArgumentException("ID оборудования обязательно");
        }
        if (dto.getParams() == null) {
            throw new IllegalArgumentException("Параметры зоны обязательны");
        }

        ZoneSectorCreateDto.ZoneParams params = dto.getParams();
        ZoneSector zoneSector = new ZoneSector();
        zoneSector.setEquipmentId(dto.getEquipmentId());
        zoneSector.setZoneType(ZoneType.CIRCLE_SECTOR);

        if (params.getCenterCoordinates() != null) {
            CoordinateDto coordModel = params.getCenterCoordinates();
            Coordinate coord = new Coordinate();
            coord.setLat(coordModel.getLat());
            coord.setLng(coordModel.getLng());
            zoneSector.setCenterCoordinates(coord);
            zoneSector.setRadiusInMeters(params.getRadiusInMeters());
            zoneSector.setStartAngle(params.getStartAngle());
            zoneSector.setEndAngle(params.getEndAngle());
        }

        return zoneSector;
    }

    public RefPoint convertToEntity(RefPointDto dto) {
        RefPoint refPoint = new RefPoint();
        refPoint.setLat(dto.getLat());
        refPoint.setLng(dto.getLng());

        return refPoint;
    }

    public ZonesResponseDto convertToResponse(List<Zone> zones) {
        if (zones == null) {
            throw new IllegalArgumentException("Список зон не может быть null");
        }

        ZonesResponseDto responseDto = new ZonesResponseDto();
        responseDto.setZones(zones);

        return responseDto;
    }

    public ZonePolygonResponseDto convertToResponse(ZonePolygon entity) {
        if (entity == null) {
            throw new IllegalArgumentException("ZonePolygon entity не может быть null");
        }

        ZonePolygonResponseDto responseDto = new ZonePolygonResponseDto();
        responseDto.setEquipmentId(entity.getEquipmentId());
        responseDto.setZoneType(ZoneType.POLYGON);

        ZonePolygonResponseDto.ZoneParams responseParams = new ZonePolygonResponseDto.ZoneParams();

        // Конвертируем координаты из Entity в DTO
        if (entity.getPointsCoordinates() != null) {
            List<CoordinateDto> coordinateDtoDtos = entity.getPointsCoordinates().stream()
                    .map(coord -> new CoordinateDto(coord.getLat(), coord.getLng()))
                    .collect(Collectors.toList());
            responseParams.setPointsCoordinates(coordinateDtoDtos);
        } else {
            responseParams.setPointsCoordinates(Collections.emptyList());
        }

        responseDto.setParams(responseParams);

        return responseDto;
    }

    public ZoneSectorResponseDto convertToResponse(ZoneSector entity) {
        if (entity == null) {
            throw new IllegalArgumentException("ZoneSector entity не может быть null");
        }

        ZoneSectorResponseDto responseDto = new ZoneSectorResponseDto();
        responseDto.setEquipmentId(entity.getEquipmentId());
        responseDto.setZoneType(ZoneType.CIRCLE_SECTOR);

        ZoneSectorResponseDto.ZoneParams params = new ZoneSectorResponseDto.ZoneParams();

        if (entity.getCenterCoordinates() != null) {
            params.setCenterCoordinates(
                    new CoordinateDto(
                            entity.getCenterCoordinates().getLat(),
                            entity.getCenterCoordinates().getLng()
                    )
            );
        }
        params.setRadiusInMeters(entity.getRadiusInMeters());
        params.setStartAngle(entity.getStartAngle());
        params.setEndAngle(entity.getEndAngle());

        responseDto.setParams(params);

        return responseDto;
    }

    public RefPointResponseDto convertToResponse(RefPoint entity) {
        if (entity == null) {
            throw new IllegalArgumentException("RefPoint entity не может быть null");
        }

        RefPointResponseDto dto = new RefPointResponseDto();
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());

        return dto;
    }
}
