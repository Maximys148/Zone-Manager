package com.stupor.zmr.dto;

import com.stupor.zmr.enums.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonePolygonResponseDto implements Serializable {
    private String equipmentId;
    private ZoneType zoneType;
    private ZoneParams params;
    @Data
    public static class ZoneParams implements Serializable {
        private List<CoordinateDto> pointsCoordinates;
    }
}
