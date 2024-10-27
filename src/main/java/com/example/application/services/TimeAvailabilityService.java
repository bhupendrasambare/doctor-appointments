/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :5:03 pm
 * Project:doctor-appointment
 **/
package com.example.application.services;

import com.example.application.dto.dto.DoctorDto;
import com.example.application.dto.enums.Constants;
import com.example.application.dto.request.FetchDoctorsRequest;
import com.example.application.dto.request.UpdateAppointmentDto;
import com.example.application.dto.response.ApiResponse;
import com.example.application.dto.response.DoctorScheduleResponse;
import com.example.application.dto.dto.DoctorTimeDto;
import com.example.application.entity.Doctor;
import com.example.application.entity.TimeAvailability;
import com.example.application.repository.DoctorRepository;
import com.example.application.repository.TimeAvailabilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimeAvailabilityService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TimeAvailabilityRepository timeAvailabilityRepository;

    // check if doctor exists
    // get doctor schedules by OpenStatus as true
    public ResponseEntity<ApiResponse> getDoctorScheduleById(Integer id) {
        ResponseEntity<ApiResponse> response = null;
        ApiResponse data = new ApiResponse();
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if(doctor!=null){
            List<DoctorTimeDto> appointments = timeAvailabilityRepository.findDtoByDoctorId(id);
            data.setData(new DoctorScheduleResponse(doctor,appointments));
            data.setMessage(Constants.DOCTOR_APPOINTMENT_FOUND);
            response = ResponseEntity.ok(data);
        }else{
            response = new ResponseEntity<ApiResponse>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.DOCTOR_NOT_FOUND_CODE,
                            Constants.DOCTOR_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return response;
    }

    // check if doctor exists
    // update the openStatus is null OR openStatus is true
    public ResponseEntity<ApiResponse> deleteDoctorAvailability(Integer id) {
        ResponseEntity<ApiResponse> response = null;
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if(doctor!=null){
            timeAvailabilityRepository.updateOpenStatusByOpenStatus(false,id);
            response = ResponseEntity.ok(new ApiResponse(Constants.DOCTOR_APPOINTMENT_DELETED));
        }else{
            response = new ResponseEntity<ApiResponse>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.DOCTOR_NOT_FOUND_CODE,
                            Constants.DOCTOR_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return response;
    }


    // 2 queries with days and without days
    // filter days also for 0> day <8 for avoiding
    public ResponseEntity<?> fetchDoctorsByFilter(FetchDoctorsRequest request) {
        if(request.getDoctorName()!=null && request.getDoctorName().isEmpty()) request.setDoctorName(null);
        List<DoctorDto> data = new ArrayList<>();
        if(request.getDays()!=null && !request.getDays().isEmpty()) {
            request.setDays(request.getDays().stream()
                    .filter(x -> x > 0 && x < 8)
                    .collect(Collectors.toSet()));
        }
        if(request.getDays()!=null && !request.getDays().isEmpty()){
            data = timeAvailabilityRepository.fetchDoctorsByFilter(
                    request.getDays(),request.getTime(),request.getDoctorName()
            );
        }else{
            data = timeAvailabilityRepository.fetchDoctorsByFilter(
                    request.getTime(),request.getDoctorName()
            );
        }
        return ResponseEntity.ok(data);
    }

    // validate doctor id
    // filter request with open flag and valid days 1-7
    // find TimeAvailability and update them according to the request data
    // validate start and end time
    // mark un updated date as OpenStatus false and start and end null
    // saveAll TimeAvailability and return success
    @Transactional
    public ResponseEntity<ApiResponse> updateDoctorAvailability(Integer id, List<UpdateAppointmentDto> request) {
        ResponseEntity<ApiResponse> response = null;
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if(doctor!=null){
            request = request.stream().filter(x->(
                    x.getOpenStatus() !=null && x.getDays()!=null
                            && x.getOpenStatus()
                            && x.getDays() > 0 && x.getDays() < 8)
            ).collect(Collectors.toList());
            List<TimeAvailability> availabilities = timeAvailabilityRepository.findByDoctorId(id);
            List<Integer> checkDays = new ArrayList<>();
            for(UpdateAppointmentDto u:request){
                if(u.getStartTime()!=null && u.getEndTime()!=null && u.getStartTime().isBefore(u.getEndTime())){
                    TimeAvailability temp = availabilities.stream().filter(x->(x.getDays()==u.getDays())).findFirst().orElse(null);
                    if(temp!=null){
                        temp.setOpenStatus(true);
                        temp.setStartTime(u.getStartTime());
                        temp.setEndTime(u.getEndTime());
                    }
                    checkDays.add(u.getDays());
                }else{
                    return new ResponseEntity<ApiResponse>(
                            new ApiResponse(
                                    Constants.Status.FAIL,
                                    Constants.START_END_TIME_VALIDITY_ERROR_CODE,
                                    "Invalid time for "+getDayNameByNumber(u.getDays())), HttpStatus.NOT_ACCEPTABLE);
                }
            }
            for(TimeAvailability t:availabilities){
                if(!checkDays.contains(t.getDays())){
                    t.setOpenStatus(false);
                    t.setStartTime(null);
                    t.setEndTime(null);
                }
            }
            timeAvailabilityRepository.saveAll(availabilities);
            response = ResponseEntity.ok(
                    new ApiResponse(Constants.DOCTOR_APPOINTMENT_UPDATED_SUCCESSFULLY));
        }else{
            response = new ResponseEntity<>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.DOCTOR_NOT_FOUND_CODE,
                            Constants.DOCTOR_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public String getDayNameByNumber(int day){
        String[] s = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        return s[day-1];
    }
}
