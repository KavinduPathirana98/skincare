package com.skincare.api.IService;

import java.util.List;

import com.skincare.api.DTO.AppointmentDTO;


public interface IAppointmentService {

    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO getAppointmentById(int id);
    boolean deleteAppointment(int id);
    AppointmentDTO updateAppointment(int id,AppointmentDTO appointmentDTO);
    AppointmentDTO addAppointment(AppointmentDTO appointmentDTO);
}
