/**
 * author @bhupendrasambare
 * Date   :26/10/24
 * Time   :4:56 pm
 * Project:doctor-appointment
 **/
package com.example.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer id;

    @Column(name = "doctor_name")
    private String doctorName;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
