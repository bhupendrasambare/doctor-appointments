/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :10:06 am
 * Project:doctor-appointment
 **/
package com.example.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Integer id;

    @Column(name = "hospital_name")
    private String hospitalName;


    @Column(name = "hospital_address")
    private String hospitalAddress;
}
