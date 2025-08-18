package com.stupor.zmr.entity;

import com.stupor.zmr.enums.ZoneType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zone_sector")
@Builder
public class ZoneSector implements Zone{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String equipmentId;
    private ZoneType zoneType;

    @JoinColumn(name = "coordinate_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Coordinate centerCoordinates;
    private Integer radiusInMeters;
    private Integer startAngle;
    private Integer endAngle;
}