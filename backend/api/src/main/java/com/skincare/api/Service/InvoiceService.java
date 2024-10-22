package com.skincare.api.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skincare.api.DTO.AppointmentDTO;
import com.skincare.api.DTO.InvoiceDTO;
import com.skincare.api.DTO.PatientDTO;
import com.skincare.api.Entity.Invoice;
import com.skincare.api.IRepository.IInvoiceRepository;
import com.skincare.api.IService.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {
	@Autowired
	IInvoiceRepository repo;

	@Override
	public List<InvoiceDTO> getAllInvoices() {
		try {
			List<InvoiceDTO> response = new ArrayList<>();

			List<Invoice> dataSet = repo.findAll();

			// Alternative Method-> this is used in below functions
			// BeanUtils.copyProperties(response, dataSet);

			dataSet.forEach(invoice -> {
				response.add(new InvoiceDTO(invoice.getId(), new AppointmentDTO(invoice.getAppointment().getId(),
						new PatientDTO(invoice.getAppointment().getPatient().getId(),
								invoice.getAppointment().getPatient().getfName(),
								invoice.getAppointment().getPatient().getlName(),
								invoice.getAppointment().getPatient().getNIC(),
								invoice.getAppointment().getPatient().getEmail(),
								invoice.getAppointment().getPatient().getPhone()),
						invoice.getAppointment().getDermatologist(), invoice.getAppointment().getAppointmentDate(),
						invoice.getAppointment().getRegistrationFee(),invoice.getAppointment().isPaid()), invoice.getTreatmentCost(), invoice.getTax(),
						invoice.getTotalAmount()));
			});
			return response;
		} catch (Exception ex) {
			throw ex;

		}
	}

	@Override
	public InvoiceDTO getInvoiceById(int id) {
		try {
			Invoice invoice = repo.findById(id).get();

			InvoiceDTO invoiceDTO = new InvoiceDTO();
			BeanUtils.copyProperties(invoice, invoiceDTO);
			return invoiceDTO;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean deleteInvoice(int id) {
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
	public InvoiceDTO updateInvoice(int id, InvoiceDTO invoiceDTO) {
		try {
			Invoice invoice = repo.findById(id).get();

			if (invoice != null) {
				invoiceDTO.setId(id);
				return addInvoice(invoiceDTO);
			} else {
				return null;
			}

		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
		try {

			Invoice invoice = new Invoice();
			InvoiceDTO returnInvoiceDTO = new InvoiceDTO();
			BeanUtils.copyProperties(invoiceDTO, invoice);
			Invoice savedInvoice = repo.save(invoice);
			BeanUtils.copyProperties(savedInvoice, returnInvoiceDTO);
			return returnInvoiceDTO;

		} catch (Exception ex) {
			throw ex;
		}
	}

}
