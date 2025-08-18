package com.stupor.zmr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneSectorUpdateDto {
    private ZoneParams params;
    @Data
    public static class ZoneParams {
        private CoordinateDto centerCoordinates;
        private Integer radiusInMeters;
        private Integer startAngle;
        private Integer endAngle;
    }
}
