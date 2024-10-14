package com.skincare.api.DTO;

public class ResponseDTO {
	
	private int responseCode;
	private String responseMsg;
	private Object data;
	
	public ResponseDTO(int responseCode, String responseMsg, Object data) {
		super();
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
		this.data = data;
	}
	
	public ResponseDTO() {
		super();
		
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	

}
