package com.stupor.zmr.repository;

import com.stupor.zmr.entity.RefPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefPointRepository extends JpaRepository<RefPoint, Integer> {
    @Query("SELECT r FROM RefPoint r ORDER BY r.id ASC LIMIT 1")
    Optional<RefPoint> findFirst();
}