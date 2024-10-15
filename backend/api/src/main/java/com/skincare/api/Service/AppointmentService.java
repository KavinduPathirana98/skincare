package com.skincare.api.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skincare.api.DTO.AppointmentDTO;
import com.skincare.api.DTO.PatientDTO;
import com.skincare.api.Entity.Appointment;
import com.skincare.api.IRepository.IAppointmentRepository;
import com.skincare.api.IService.IAppointmentService;


@Service
public class AppointmentService implements IAppointmentService {
	@Autowired
	IAppointmentRepository repo;
	
	@Override
	public List<AppointmentDTO> getAllAppointments() {
		try {
			List<AppointmentDTO> response = new ArrayList<>();

			List<Appointment> dataSet = repo.findAll();
			dataSet.forEach(appoint -> {
				response.add(new AppointmentDTO(appoint.getId(),
						new PatientDTO(appoint.getPatient().getId(),
								appoint.getPatient().getfName(),
								appoint.getPatient().getlName(),
								appoint.getPatient().getNIC(),
								appoint.getPatient().getEmail(),
								appoint.getPatient().getPhone()), 
						appoint.getDermatologist(), appoint.getAppointmentDate(),
						appoint.getRegistrationFee()));
			});
			return response;
		} catch (Exception ex) {
			throw ex;
			
		}
	}

	@Override
	public AppointmentDTO getAppointmentById(int id) {
		try {
			Appointment appoint = repo.findById(id).get();

			AppointmentDTO appointDTO = new AppointmentDTO();
			BeanUtils.copyProperties(appoint, appointDTO);
			return appointDTO;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean deleteAppointment(int id) {
		try {
			repo.delete(repo.findById(id).get());
			return true;
		} catch (NoSuchElementException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public AppointmentDTO updateAppointment(int id, AppointmentDTO appointmentDTO) {
		try {
			Appointment appoint = repo.findById(id).get();

			if (appoint != null) {
				appointmentDTO.setId(id);
				return addAppointment(appointmentDTO);
			} else {
				return null;
			}

		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
		try {
			
		 	Appointment appoint = new Appointment();
		 	AppointmentDTO returnAppointDTO = new AppointmentDTO();
	        BeanUtils.copyProperties(appointmentDTO, appoint);
	        Appointment savedAppoint = repo.save(appoint);
	        BeanUtils.copyProperties(savedAppoint, returnAppointDTO);
	        return returnAppointDTO;

	} catch (Exception ex) {
		throw ex;
	}
	}

}
