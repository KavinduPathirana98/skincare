package com.skincare.api.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skincare.api.DTO.AppointmentDTO;
import com.skincare.api.DTO.PatientDTO;
import com.skincare.api.Entity.Appointment;
import com.skincare.api.Entity.Patient;
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
	        // Retrieve the existing Appointment entity by ID
	        Appointment appoint = repo.findById(id).orElse(null);

	        if (appoint != null) {
	            // Prepare the AppointmentDTO to return
	            AppointmentDTO appointDTO = new AppointmentDTO();
	            BeanUtils.copyProperties(appoint, appointDTO);

	            // Manually map the Patient entity to PatientDTO
	            if (appoint.getPatient() != null) {
	                PatientDTO patientDTO = new PatientDTO();
	                BeanUtils.copyProperties(appoint.getPatient(), patientDTO);
	                appointDTO.setPatient(patientDTO); // Set the patient in the returned AppointmentDTO
	            }

	            return appointDTO; // Return the populated AppointmentDTO
	        } else {
	            return null; // Return null if the appointment was not found
	        }
	    } catch (Exception ex) {
	        throw ex; // Rethrow the exception for handling elsewhere
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
	        // Retrieve the existing Appointment entity by ID
	        Appointment appoint = repo.findById(id).orElse(null);

	        if (appoint != null) {
	            // Set the ID from the appointmentDTO to maintain the same ID
	            appointmentDTO.setId(id);

	            // Copy other properties from AppointmentDTO to Appointment entity, excluding 'patient'
	            BeanUtils.copyProperties(appointmentDTO, appoint, "id", "patient");

	            // Save the updated Appointment entity
	            Appointment savedAppoint = repo.save(appoint);

	            // Prepare the returned AppointmentDTO
	            AppointmentDTO returnAppointDTO = new AppointmentDTO();
	            BeanUtils.copyProperties(savedAppoint, returnAppointDTO);

	            // Manually map the existing Patient entity to the returned AppointmentDTO
	            if (savedAppoint.getPatient() != null) {
	                PatientDTO savedPatientDTO = new PatientDTO();
	                BeanUtils.copyProperties(savedAppoint.getPatient(), savedPatientDTO);
	                returnAppointDTO.setPatient(savedPatientDTO); // Set the existing patient in the returned DTO
	            }

	            return returnAppointDTO; // Return the updated appointment DTO
	        } else {
	            return null; // Return null if the appointment was not found
	        }
	    } catch (Exception ex) {
	        throw ex; // Rethrow the exception for handling elsewhere
	    }
	}



	@Override
	public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
	    try {
	        // Create new Appointment entity
	        Appointment appoint = new Appointment();

	        // Manually map PatientDTO to the Appointment entity's Patient field
	        Patient patientEntity = new Patient();
	        PatientDTO patientDTO = appointmentDTO.getPatient();
	        if (patientDTO != null) {
	            // Map PatientDTO properties to Patient entity
	            BeanUtils.copyProperties(patientDTO, patientEntity);
	            appoint.setPatient(patientEntity); // Set the patient in the Appointment entity
	        }

	        // Copy other properties from AppointmentDTO to Appointment entity
	        BeanUtils.copyProperties(appointmentDTO, appoint, "patient"); // Exclude 'patient' to prevent overwriting

	        // Save the Appointment entity
	        Appointment savedAppoint = repo.save(appoint);

	        // Map saved Appointment entity to AppointmentDTO
	        AppointmentDTO returnAppointDTO = new AppointmentDTO();
	        BeanUtils.copyProperties(savedAppoint, returnAppointDTO);

	        // Manually map saved Patient entity to the returned AppointmentDTO
	        if (savedAppoint.getPatient() != null) {
	            PatientDTO savedPatientDTO = new PatientDTO();
	            BeanUtils.copyProperties(savedAppoint.getPatient(), savedPatientDTO);
	            returnAppointDTO.setPatient(savedPatientDTO); // Set the patient in the returned DTO
	        }

	        return returnAppointDTO;

	    } catch (Exception ex) {
	        throw ex;
	    }
	}
	
	@Override
	public List<AppointmentDTO> getAppointmentsByDate(LocalDateTime date) {
	    try {
	        // Retrieve appointments by date
	        List<Appointment> appointments = repo.findByAppointmentDate(date);

	        // Map appointments to AppointmentDTOs
	        List<AppointmentDTO> appointmentDTOs = appointments.stream()
	            .map(appointment -> {
	                AppointmentDTO dto = new AppointmentDTO();
	                // Copy properties from Appointment to AppointmentDTO
	                BeanUtils.copyProperties(appointment, dto);

	                // Manually map Patient entity to PatientDTO
	                if (appointment.getPatient() != null) {
	                    PatientDTO patientDTO = new PatientDTO();
	                    BeanUtils.copyProperties(appointment.getPatient(), patientDTO);
	                    dto.setPatient(patientDTO); // Set the patient in the AppointmentDTO
	                }
	                
	                return dto;
	            })
	            .collect(Collectors.toList());

	        return appointmentDTOs;

	    } catch (Exception ex) {
	        throw ex; // Rethrow the exception for handling elsewhere
	    }
	}

}
