package com.skincare.api.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skincare.api.DTO.TreatmentDTO;
import com.skincare.api.Entity.Treatment;
import com.skincare.api.IRepository.ITreatmentRepository;
import com.skincare.api.IService.ITreatmentService;

@Service
public class TreatmentService implements ITreatmentService {

	@Autowired
	ITreatmentRepository repo;

	@Override
	public List<TreatmentDTO> getAllTreatments() {
		try {
			List<TreatmentDTO> response = new ArrayList<>();

			List<Treatment> dataSet = repo.findAll();
			dataSet.forEach(treatment -> {
				response.add(new TreatmentDTO(treatment.getId(), treatment.getTreatmentName(), treatment.getPrice()));
			});
			return response;
		} catch (Exception ex) {
			throw ex;

		}

	}

	@Override
	public TreatmentDTO getTreatmentById(int id) {
		try {
			Treatment treatment = repo.findById(id).get();

			TreatmentDTO treatmentDTO = new TreatmentDTO();
			BeanUtils.copyProperties(treatment, treatmentDTO);
			return treatmentDTO;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean deleteTreatment(int id) {
		try {
			repo.delete(repo.findById(id).get());
			return true;
		} catch (NoSuchElementException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public TreatmentDTO updateTreatment(int id, TreatmentDTO treatmentDTO) {

		try {
			Treatment treatment = repo.findById(id).get();

			if (treatment != null) {
				treatmentDTO.setId(id);
				return addTreatment(treatmentDTO);
			} else {
				return null;
			}

		} catch (Exception ex) {
			throw ex;
		}

	}

	@Override
	public TreatmentDTO addTreatment(TreatmentDTO treatmentDTO) {

		try {

			Treatment treatment = new Treatment();
			TreatmentDTO returnTreatmentDTO = new TreatmentDTO();
			BeanUtils.copyProperties(treatmentDTO, treatment);
			Treatment savedTreatment = repo.save(treatment);
			BeanUtils.copyProperties(savedTreatment, returnTreatmentDTO);
			return returnTreatmentDTO;

		} catch (Exception ex) {
			throw ex;
		}

	}
}
