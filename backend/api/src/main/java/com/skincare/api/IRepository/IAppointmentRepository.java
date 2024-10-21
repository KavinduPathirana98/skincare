package com.skincare.api.IRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skincare.api.Entity.Appointment;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer>{
	// Method to find appointments by date
    List<Appointment> findByAppointmentDate(LocalDateTime appointmentDate);
}
