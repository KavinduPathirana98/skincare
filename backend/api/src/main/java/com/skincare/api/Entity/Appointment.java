package com.skincare.api.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "id")
	    private Patient patient;

	    private String dermatologist;
	    private LocalDateTime appointmentDate;
	    private double registrationFee = 500.00;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Patient getPatient() {
			return patient;
		}
		public void setPatient(Patient patient) {
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
