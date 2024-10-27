package com.example.application.repository;

import com.example.application.dto.dto.DoctorDto;
import com.example.application.dto.dto.DoctorTimeDto;
import com.example.application.entity.TimeAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TimeAvailabilityRepository extends JpaRepository<TimeAvailability,Integer> {

    @Query("Select new com.example.application.dto.dto.DoctorTimeDto(" +
            " u.id, u.days, u.openStatus, u.startTime, u.endTime " +
            ") from TimeAvailability u where u.doctor.id = ?1")
    List<DoctorTimeDto> findDtoByDoctorId(Integer doctorId);

    @Transactional
    @Modifying
    @Query("update TimeAvailability t set t.openStatus = ?1 where t.doctor.id = ?2 and (t.openStatus is null or t.openStatus != ?1)")
    void updateOpenStatusByOpenStatus(@NonNull Boolean openStatus, Integer doctorId);


//    @Query("Select new com.example.application.dto.dto.DoctorDto(u.id, u.doctorName" +
//            " , u.hospital.id, u.hospital.hospitalName, u.hospital.hospitalAddress) from Doctor u where u.id in (" +
//            " Select DISTINCT t.doctor.id from TimeAvailability t where u.days in ?1 and " +
//            " (?2 is null OR ?2 BETWEEN t.startTime AND t.endTime) AND (?3 is null OR t.doctor.doctorName %?3%)" +
//            ")")
    @Query("SELECT DISTINCT new com.example.application.dto.dto.DoctorDto(t.doctor.id, t.doctor.doctorName, " +
            " t.doctor.hospital.id, t.doctor.hospital.hospitalName, t.doctor.hospital.hospitalAddress) " +
            " FROM TimeAvailability t " +
            " WHERE t.days IN ?1 " +
            " AND (?2 IS NULL OR ?2 BETWEEN t.startTime AND t.endTime) " +
            " AND (?3 IS NULL OR t.doctor.doctorName LIKE %?3%) AND t.openStatus = true")
    List<DoctorDto> fetchDoctorsByFilter(Set<Integer> days, LocalTime time, String doctorName);


//    @Query("Select new com.example.application.dto.dto.DoctorDto(u.id, u.doctorName" +
//            " , u.hospital.id, u.hospital.hospitalName, u.hospital.hospitalAddress) from Doctor u where u.id in (" +
//            " Select DISTINCT t.doctor.id from TimeAvailability t where " +
//            " (?1 is null OR ?1 BETWEEN t.startTime AND t.endTime) AND (?2 is null OR t.doctor.doctorName %?2%)" +
//            ")")
    @Query("SELECT DISTINCT new com.example.application.dto.dto.DoctorDto(t.doctor.id, t.doctor.doctorName, " +
            " t.doctor.hospital.id, t.doctor.hospital.hospitalName, t.doctor.hospital.hospitalAddress) " +
            " FROM TimeAvailability t " +
            " WHERE (?1 IS NULL OR ?1 BETWEEN t.startTime AND t.endTime) " +
            " AND (?2 IS NULL OR t.doctor.doctorName LIKE %?2%) AND t.openStatus = true")
    List<DoctorDto> fetchDoctorsByFilter(LocalTime time, String doctorName);

    @Query("Select u from TimeAvailability u where u.doctor.id = ?1")
    List<TimeAvailability> findByDoctorId(Integer id);
}
