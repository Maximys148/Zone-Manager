package com.stupor.zmr.dto;

import com.stupor.zmr.enums.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneSectorResponseDto implements Serializable {
    private String equipmentId;
    private ZoneType zoneType;
    private ZoneParams params;
    @Data
    public static class ZoneParams implements Serializable {
        private CoordinateDto centerCoordinates;
        private Integer radiusInMeters;
        private Integer startAngle;
        private Integer endAngle;
    }
}
