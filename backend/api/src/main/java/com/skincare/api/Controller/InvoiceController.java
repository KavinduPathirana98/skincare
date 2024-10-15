package com.skincare.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skincare.api.DTO.AppointmentDTO;
import com.skincare.api.DTO.InvoiceDTO;
import com.skincare.api.DTO.PatientDTO;
import com.skincare.api.DTO.ResponseDTO;
import com.skincare.api.IService.IAppointmentService;
import com.skincare.api.IService.IInvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	@Autowired
	IInvoiceService service;

	//Get All Invoices
	@GetMapping
	public ResponseDTO getAllInvoices() {

		try {
			List<InvoiceDTO> all = service.getAllInvoices();
			return new ResponseDTO(1, "Success", all);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Add New Invoice
	@PostMapping
	public ResponseDTO saveInvoice(@RequestBody InvoiceDTO invoiceDTO) {

		try {
			InvoiceDTO response = service.addInvoice(invoiceDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Update Invoice By ID
	@PutMapping("/{id}")
	public ResponseDTO updateInvoice(@PathVariable("id") int id, @RequestBody InvoiceDTO invoiceDTO) {

		try {
			InvoiceDTO response = service.updateInvoice(id, invoiceDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {

			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Delete Invoice By ID
	@DeleteMapping("/{id}")
	public ResponseDTO deleteInvoice(@PathVariable("id") int id) {

		try {
			if (service.deleteInvoice(id)) {
				return new ResponseDTO(1, "Successfully Deleted Invoice", "");
			} else {
				return new ResponseDTO(1, "No Such Invoice", "");
			}
		}
		catch(Exception ex)
		{
			return new ResponseDTO(0, ex.getMessage().toString(), "");
		}


	}

	//Find Invoice By ID
	@GetMapping("/{id}")
	public ResponseDTO findInvoiceById(@PathVariable("id") int id) {

		try {
			InvoiceDTO invoiceById = service.getInvoiceById(id);
			if (invoiceById != null) {
				return new ResponseDTO(1, "Success", invoiceById);
			} else {
				return new ResponseDTO(1, "No invoice",null);
			}	
		}
		catch(Exception ex)
		{
			return new ResponseDTO(0, "No User", null);
		}
		
	}
}
