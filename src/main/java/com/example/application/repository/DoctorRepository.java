/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :5:02 pm
 * Project:doctor-appointment
 **/
package com.example.application.repository;

import com.example.application.dto.dto.DoctorDto;
import com.example.application.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    @Query("Select new com.example.application.dto.dto.DoctorDto(u.id, u.doctorName" +
            " , u.hospital.id, u.hospital.hospitalName, u.hospital.hospitalAddress) from Doctor u")
    List<DoctorDto> getAllByDto();
}
