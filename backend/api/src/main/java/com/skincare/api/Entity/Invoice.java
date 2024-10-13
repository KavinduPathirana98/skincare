package com.skincare.api.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Invoice {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    @JoinColumn(name = "appointment_id")
	    private Appointment appointment;

	    private double treatmentCost;
	    private double tax;
	    private double totalAmount;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Appointment getAppointment() {
			return appointment;
		}
		public void setAppointment(Appointment appointment) {
			this.appointment = appointment;
		}
		public double getTreatmentCost() {
			return treatmentCost;
		}
		public void setTreatmentCost(double treatmentCost) {
			this.treatmentCost = treatmentCost;
		}
		public double getTax() {
			return tax;
		}
		public void setTax(double tax) {
			this.tax = tax;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
	    
	    
}
