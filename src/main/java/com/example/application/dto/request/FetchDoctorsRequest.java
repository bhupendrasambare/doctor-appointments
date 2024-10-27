/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :1:05 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchDoctorsRequest {
    private String doctorName;
    private Set<Integer> days;
    private LocalTime time;
}
