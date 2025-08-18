package com.stupor.zmr.entity;

import com.stupor.zmr.enums.ZoneType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zone_polygon")
@Builder
public class ZonePolygon implements Zone{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String equipmentId;
    private ZoneType zoneType;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "zone_polygon_coordinates",
            joinColumns = @JoinColumn(name = "zone_polygon_id"),
            inverseJoinColumns = @JoinColumn(name = "coordinate_id")
    )
    private List<Coordinate> pointsCoordinates;
}