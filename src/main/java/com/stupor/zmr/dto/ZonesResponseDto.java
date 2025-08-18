package com.stupor.zmr.dto;

import com.stupor.zmr.entity.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonesResponseDto implements Serializable {
    private List<Zone> zones;
}
