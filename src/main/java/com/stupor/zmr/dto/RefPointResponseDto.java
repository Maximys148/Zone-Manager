package com.stupor.zmr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefPointResponseDto implements Serializable {
    private Double lat;
    private Double lng;
}
