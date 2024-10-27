/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :12:21 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.response;

import com.example.application.dto.dto.DoctorTimeDto;
import com.example.application.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorScheduleResponse {
    private Integer doctorId;
    private String doctorName;

    private Integer hospitalId;
    private String hospitalName;
    private String hospitalAddress;

    List<DoctorTimeDto> appointments;

    public DoctorScheduleResponse(Doctor doctor,List<DoctorTimeDto> appointments){
        this.doctorId = doctor.getId();
        this.doctorName = doctor.getDoctorName();
        if(doctor.getHospital()!=null){
            this.hospitalId = doctor.getHospital().getId();
            this.hospitalName = doctor.getHospital().getHospitalName();
            this.hospitalAddress = doctor.getHospital().getHospitalAddress();
        }
        this.appointments = appointments;
    }
}
