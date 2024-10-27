/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :2:11 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentDto {
    private Integer days;
    private Boolean openStatus;
    private LocalTime startTime;
    private LocalTime endTime;
}
