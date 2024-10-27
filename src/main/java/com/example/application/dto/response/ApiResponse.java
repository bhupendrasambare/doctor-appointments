/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :12:07 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.response;

import com.example.application.dto.enums.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String code = Constants.SUCCESS_CODE;
    private Constants.Status status = Constants.Status.SUCCESS;
    private String message = Constants.OPERATION_SUCCESS;
    private Object data;

    public ApiResponse(String message){
        this.message = message;
    }

    public ApiResponse(String message,Object data){
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Constants.Status status,String code,  String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
