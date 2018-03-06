package com.lnet.ums.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestModel implements Serializable {
    private String vehicleTypeId;
    private String name;
    private Double length;
    private Double width;
    private Double height;
    private Double loadVolume;
    private Double loadWeight;
}
