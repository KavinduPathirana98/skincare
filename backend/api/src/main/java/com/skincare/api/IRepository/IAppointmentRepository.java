package com.skincare.api.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skincare.api.Entity.Appointment;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer>{

}
