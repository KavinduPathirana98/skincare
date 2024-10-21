package com.skincare.api.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skincare.api.DTO.AppointmentDTO;
import com.skincare.api.DTO.ResponseDTO;
import com.skincare.api.IService.IAppointmentService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AppointmentController {

	@Autowired
	IAppointmentService service;

	//Get All Appointments
	@GetMapping
	public ResponseDTO getAllAppointments() {

		try {
			List<AppointmentDTO> all = service.getAllAppointments();
			return new ResponseDTO(1, "Success", all);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Add New Appointment
	@PostMapping
	public ResponseDTO saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {

		try {
			AppointmentDTO response = service.addAppointment(appointmentDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Update Appointment By ID
	@PutMapping("/{id}")
	public ResponseDTO updateAppointment(@PathVariable("id") int id, @RequestBody AppointmentDTO appointmentDTO) {

		try {
			AppointmentDTO response = service.updateAppointment(id, appointmentDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {

			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Delete Appointment By ID
	@DeleteMapping("/{id}")
	public ResponseDTO deleteAppointment(@PathVariable("id") int id) {

		try {
			if (service.deleteAppointment(id)) {
				return new ResponseDTO(1, "Successfully Deleted Appointment", "");
			} else {
				return new ResponseDTO(1, "No Such Appointment", "");
			}
		}
		catch(Exception ex)
		{
			return new ResponseDTO(0, ex.getMessage().toString(), "");
		}


	}

	//Find Appointment By ID
	@GetMapping("/{id}")
	public ResponseDTO findAppointmentById(@PathVariable("id") int id) {

		try {
			AppointmentDTO appointmentById = service.getAppointmentById(id);
			if (appointmentById != null) {
				return new ResponseDTO(1, "Success", appointmentById);
			} else {
				return new ResponseDTO(1, "No appointment",null);
			}	
		}
		catch(Exception ex)
		{
			return new ResponseDTO(0, "No User", null);
		}
		
	}
	
	   // New method to filter appointments by date
    @GetMapping("/date/{date}")
    public ResponseDTO findAppointmentsByDate(@PathVariable("date") String date) {
        try {
            // Parse the date string to LocalDate
            LocalDateTime appointmentDate = LocalDateTime.parse(date);
            List<AppointmentDTO> appointments = service.getAppointmentsByDate(appointmentDate);
            
            if (!appointments.isEmpty()) {
                return new ResponseDTO(1, "Success", appointments);
            } else {
                return new ResponseDTO(1, "No appointments found for the given date", null);
            }
        } catch (Exception ex) {
            return new ResponseDTO(0, "Error retrieving appointments", null);
        }
    }
}
