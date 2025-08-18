package com.stupor.zmr.repository;

import com.stupor.zmr.entity.Coordinate;
import com.stupor.zmr.entity.ZonePolygon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZonePolygonRepository extends JpaRepository<ZonePolygon, String> {
    ZonePolygon findZonePolygonById(String id);
    boolean existsByEquipmentId(String equipmentId);

    ZonePolygon findByEquipmentId(String equipmentId);

    @EntityGraph(attributePaths = "pointsCoordinates")
    @Override
    List<ZonePolygon> findAll();

    @EntityGraph(attributePaths = "pointsCoordinates")
    @Query("SELECT zp FROM ZonePolygon zp")
    List<ZonePolygon> findAllWithCoordinates();

    @Modifying
    @Query("UPDATE ZonePolygon z SET " +
            "z.equipmentId = :equipmentId, " +
            "z.pointsCoordinates = :coordinates " +
            "WHERE z.id = :id")
    int updateZonePolygon(
            @Param("id") String id,
            @Param("equipmentId") String equipmentId,
            @Param("coordinates") List<Coordinate> coordinates
    );

    void deleteById(String Id);
    boolean existsByEquipmentIdAndIdNot(String equipmentId, String id);
}

