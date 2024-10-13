package com.skincare.api.DTO;



public class PatientDTO {

	
	private int id;
	private String fName;
	private String lName;
	private String NIC;
	private String email;
	private int phone;
	
	
	
	public PatientDTO() {
		super();
		
	}
	
	public PatientDTO(int id, String fName, String lName, String nIC, String email, int phone) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		NIC = nIC;
		this.email = email;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getNIC() {
		return NIC;
	}
	public void setNIC(String nIC) {
		NIC = nIC;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	
	
	
	
}
