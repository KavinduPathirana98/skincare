package com.skincare.api.IService;

import java.util.List;


import com.skincare.api.DTO.TreatmentDTO;

public interface ITreatmentService {

	 	List<TreatmentDTO> getAllTreatments();
	 	TreatmentDTO getTreatmentById(int id);
	    boolean deleteTreatment(int id);
	    TreatmentDTO updateTreatment(int id,TreatmentDTO treatmentDTO);
	    TreatmentDTO addTreatment(TreatmentDTO treatmentDTO);
}
