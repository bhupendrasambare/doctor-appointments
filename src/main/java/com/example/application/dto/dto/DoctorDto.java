/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :5:12 pm
 * Project:doctor-appointment
 **/
package com.example.application.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoctorDto {
    private Integer doctorId;
    private String doctorName;

    private Integer hospitalId;
    private String hospitalName;
    private String hospitalAddress;

    public DoctorDto(Integer doctorId, String doctorName, Integer hospitalId, String hospitalName, String hospitalAddress) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
    }
}
