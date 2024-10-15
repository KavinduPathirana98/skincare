package com.skincare.api.DTO;

import java.time.LocalDateTime;

public class AppointmentDTO {

	    private int id;
	    private PatientDTO patient;
	    private String dermatologist;
	    private LocalDateTime appointmentDate;
	    private double registrationFee = 500.00;
	    
	    
		public AppointmentDTO() {
			super();
		}

		public AppointmentDTO(int id, PatientDTO patient, String dermatologist, LocalDateTime appointmentDate,
			double registrationFee) {
			super();
			this.id = id;
			this.patient = patient;
			this.dermatologist = dermatologist;
			this.appointmentDate = appointmentDate;
			this.registrationFee = registrationFee;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public PatientDTO getPatient() {
			return patient;
		}
		public void setPatient(PatientDTO patient) {
			this.patient = patient;
		}
		public String getDermatologist() {
			return dermatologist;
		}
		public void setDermatologist(String dermatologist) {
			this.dermatologist = dermatologist;
		}
		public LocalDateTime getAppointmentDate() {
			return appointmentDate;
		}
		public void setAppointmentDate(LocalDateTime appointmentDate) {
			this.appointmentDate = appointmentDate;
		}
		public double getRegistrationFee() {
			return registrationFee;
		}
		public void setRegistrationFee(double registrationFee) {
			this.registrationFee = registrationFee;
		}
	    
	    
}
