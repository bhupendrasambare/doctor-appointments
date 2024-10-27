/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :5:03 pm
 * Project:doctor-appointment
 **/
package com.example.application.services;

import com.example.application.dto.dto.DoctorDto;
import com.example.application.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<DoctorDto> getAllByDto() {
        return doctorRepository.getAllByDto();
    }
}
