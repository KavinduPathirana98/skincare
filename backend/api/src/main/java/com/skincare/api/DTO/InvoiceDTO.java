package com.skincare.api.DTO;


public class InvoiceDTO {

	    private int id;
	    private AppointmentDTO appointment;
	    private double treatmentCost;
	    private double tax;
	    private double totalAmount;

	    
		public InvoiceDTO() {
			super();
		}
		public InvoiceDTO(int id, AppointmentDTO appointment, double treatmentCost, double tax, double totalAmount) {
			super();
			this.id = id;
			this.appointment = appointment;
			this.treatmentCost = treatmentCost;
			this.tax = tax;
			this.totalAmount = totalAmount;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public AppointmentDTO getAppointment() {
			return appointment;
		}
		public void setAppointment(AppointmentDTO appointment) {
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
