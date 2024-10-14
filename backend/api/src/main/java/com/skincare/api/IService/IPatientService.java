package com.skincare.api.IService;

import java.util.List;

import com.skincare.api.DTO.PatientDTO;

public interface IPatientService {
	
    List<PatientDTO> getAllPatients();
    PatientDTO getPatientById(int id);
    boolean deletePatient(int id);
    PatientDTO updatePatient(int id,PatientDTO patientDTO);
    PatientDTO addPatient(PatientDTO patientDTO);

}
