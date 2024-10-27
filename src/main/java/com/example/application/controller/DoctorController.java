/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :5:04 pm
 * Project:doctor-appointment
 **/
package com.example.application.controller;

import com.example.application.dto.enums.Constants;
import com.example.application.dto.request.FetchDoctorsRequest;
import com.example.application.dto.request.UpdateAppointmentDto;
import com.example.application.dto.response.ApiResponse;
import com.example.application.dto.dto.DoctorDto;
import com.example.application.services.DoctorService;
import com.example.application.services.TimeAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Doctors Controller", description = "APIs for Doctors details")
@RestController
@RequestMapping("/api/v1/doctor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TimeAvailabilityService timeAvailabilityService;

    @Operation(summary = "Get doctors", description = "Get all Doctors with Hospital details too")
    @GetMapping("/get")
    public List<DoctorDto> getAll(){
        return doctorService.getAllByDto();
    }

    @Operation(summary = "Get doctor schedules", description = "Get Doctors all availability")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getDoctorScheduleNById(@PathVariable(name = "id") Integer id){
        try{
            return timeAvailabilityService.getDoctorScheduleById(id);
        }catch (Exception e){
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.INTERNAL_SERVER_ERROR_CODE,
                            Constants.SOMETHING_WENT_WRONG), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete doctor's all availability", description = "Delete doctor's all availability by doctor id")
    @DeleteMapping("/delete-availability/{id}")
    public ResponseEntity<ApiResponse> deleteDoctorAvailability(@PathVariable(name = "id") Integer id){
        try{
            return timeAvailabilityService.deleteDoctorAvailability(id);
        }catch (Exception e){
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.INTERNAL_SERVER_ERROR_CODE,
                            Constants.SOMETHING_WENT_WRONG), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update doctor's availability", description = "Update doctor's availability by doctor id")
    @PostMapping("/update-availability/{id}")
    public ResponseEntity<ApiResponse> updateDoctorAvailability(@PathVariable(name = "id") Integer id,@RequestBody List<UpdateAppointmentDto> request){
        try{
            if(request!=null){
                return timeAvailabilityService.updateDoctorAvailability(id,request);
            }else{
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(
                                Constants.Status.FAIL,
                                Constants.BAD_REQUEST_CODE,
                                Constants.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.INTERNAL_SERVER_ERROR_CODE,
                            Constants.SOMETHING_WENT_WRONG), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Fetch doctors by filters", description = "Find doctors by name, available day and available time")
    @PostMapping("/fetch")
    public ResponseEntity<?> fetchDoctorsByFilter(@RequestBody FetchDoctorsRequest request){
        try{
            return timeAvailabilityService.fetchDoctorsByFilter(request);
        }catch (Exception e){
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(
                            Constants.Status.FAIL,
                            Constants.INTERNAL_SERVER_ERROR_CODE,
                            Constants.SOMETHING_WENT_WRONG), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
