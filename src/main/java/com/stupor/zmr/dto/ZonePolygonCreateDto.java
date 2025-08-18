package com.stupor.zmr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonePolygonCreateDto {
    private String equipmentId;
    private ZoneParams params;
    @Data
    public static class ZoneParams {
        private List<CoordinateDto> pointsCoordinates;
    }
}