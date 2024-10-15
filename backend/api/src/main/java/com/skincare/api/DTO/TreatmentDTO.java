package com.skincare.api.DTO;


public class TreatmentDTO {
  
    private int id;

    private String treatmentName;
    private double price;
    
	public TreatmentDTO() {
		super();

	}
	
	public TreatmentDTO(int id, String treatmentName, double price) {
		super();
		this.id = id;
		this.treatmentName = treatmentName;
		this.price = price;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    
    
}


