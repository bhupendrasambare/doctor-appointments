/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :12:24 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class DoctorTimeDto {
    private Integer timeId;
    private Integer days;
    private Boolean openStatus;
    private LocalTime startTime;
    private LocalTime endTime;

    public DoctorTimeDto(Integer timeId, Integer days, Boolean openStatus, LocalTime startTime, LocalTime endTime) {
        this.timeId = timeId;
        this.days = days;
        this.openStatus = openStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
