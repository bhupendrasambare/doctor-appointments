/**
 * author @bhupendrasambare
 * Date   :27/10/24
 * Time   :1:20 am
 * Project:doctor-appointment
 **/
package com.example.application.dto.dto;

import java.util.List;

public class PagingResponse <T>{
    public List<T> data;
    public Long total;
}
