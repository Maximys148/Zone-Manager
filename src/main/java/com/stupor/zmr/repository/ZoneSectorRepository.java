package com.stupor.zmr.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.stupor.zmr.entity.ZoneSector;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ZoneSectorRepository extends JpaRepository<ZoneSector, String> {
    boolean existsByEquipmentId(String equipmentId);

    ZoneSector findByEquipmentId(String equipmentId);

    @EntityGraph(attributePaths = "centerCoordinates")
    @Override
    List<ZoneSector> findAll();

    @EntityGraph(attributePaths = "centerCoordinates")
    @Query("SELECT zs FROM ZoneSector zs")
    List<ZoneSector> findAllWithCenter();

    boolean existsByEquipmentIdAndIdNot(String equipmentId, String id);
}