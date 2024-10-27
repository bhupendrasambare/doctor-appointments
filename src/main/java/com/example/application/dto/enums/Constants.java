/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :12:08 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.enums;

public class Constants {

    public static final String SUCCESS_CODE = "200";
    public static final String CREATED_CODE = "201";
    public static final String DOCTOR_NOT_FOUND_CODE = "404";
    public static final String START_END_TIME_VALIDITY_ERROR_CODE = "405";
    public static final String INTERNAL_SERVER_ERROR_CODE = "500";
    public static final String BAD_REQUEST_CODE = "400";

    public static final String OPERATION_SUCCESS = "Operation success";
    public static final String DOCTOR_APPOINTMENT_FOUND = "Doctor appointments found";
    public static final String DOCTOR_NOT_FOUND = "Doctor not found";
    public static final String DOCTOR_APPOINTMENT_DELETED = "Doctor appointments deleted";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String INVALID_REQUEST = "Bad request";
    public static final String DOCTOR_APPOINTMENT_UPDATED_SUCCESSFULLY = "Doctors appointment updated successfully";

    public enum Status{
        SUCCESS, FAIL
    }
}
