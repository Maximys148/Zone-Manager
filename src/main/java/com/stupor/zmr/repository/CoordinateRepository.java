package com.stupor.zmr.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.stupor.zmr.entity.Coordinate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    List<Coordinate> findAllById(long zoneId);

    // Для ZonePolygon (удаление связей из join-таблицы)
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM zone_polygon_coordinates WHERE zone_polygon_id = :zoneId")
    void deletePolygonCoordinateLinks(@Param("zoneId") String zoneId);

    // Для ZoneSector (удаление центральной координаты)
    @Modifying
    @Transactional
    @Query("UPDATE ZoneSector z SET z.centerCoordinates = null WHERE z.id = :zoneId")
    void detachSectorCenterCoordinate(@Param("zoneId") String zoneId);

    // Поиск "осиротевших" координат
    @Query("SELECT c FROM Coordinate c WHERE " +
            "c.id NOT IN (SELECT c.id FROM ZonePolygon zp JOIN zp.pointsCoordinates c) AND " +
            "c.id NOT IN (SELECT zs.centerCoordinates.id FROM ZoneSector zs WHERE zs.centerCoordinates IS NOT NULL)")
    List<Coordinate> findOrphanedCoordinates();
}