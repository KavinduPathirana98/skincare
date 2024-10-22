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
import com.skincare.api.Entity.Appointment;
import com.skincare.api.Entity.Invoice;
import com.skincare.api.Entity.Patient;
import com.skincare.api.IRepository.IAppointmentRepository;
import com.skincare.api.IRepository.IInvoiceRepository;
import com.skincare.api.IService.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    IInvoiceRepository repo;
    @Autowired
    IAppointmentRepository appointmentRepo;

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        try {
            List<InvoiceDTO> response = new ArrayList<>();

            List<Invoice> dataSet = repo.findAll();
            dataSet.forEach(invoice -> {
                // Check if the appointment is not null
                Appointment appointment = invoice.getAppointment();
                AppointmentDTO appointmentDTO = null;

                if (appointment != null) {
                    // Check if the patient is not null
                    Patient patient = appointment.getPatient();
                    PatientDTO patientDTO = null;

                    if (patient != null) {
                        patientDTO = new PatientDTO(
                                patient.getId(),
                                patient.getfName(),
                                patient.getlName(),
                                patient.getNIC(),
                                patient.getEmail(),
                                patient.getPhone());
                    }

                    appointmentDTO = new AppointmentDTO(
                            appointment.getId(),
                            patientDTO, // Set the patientDTO here
                            appointment.getDermatologist(),
                            appointment.getAppointmentDate(),
                            appointment.getRegistrationFee(),
                            appointment.isPaid());
                }

                // Add the InvoiceDTO to the response list
                response.add(new InvoiceDTO(
                        invoice.getId(),
                        appointmentDTO,  // It can be null if the appointment is missing
                        invoice.getTreatmentCost(),
                        invoice.getTax(),
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
            Invoice invoice = repo.findById(id).orElse(null);

            if (invoice != null) {
                InvoiceDTO invoiceDTO = new InvoiceDTO();
                BeanUtils.copyProperties(invoice, invoiceDTO);

                // Manually map Appointment and Patient
                Appointment appointment = invoice.getAppointment();
                if (appointment != null) {
                    AppointmentDTO appointmentDTO = new AppointmentDTO();
                    BeanUtils.copyProperties(appointment, appointmentDTO);

                    Patient patient = appointment.getPatient();
                    if (patient != null) {
                        PatientDTO patientDTO = new PatientDTO();
                        BeanUtils.copyProperties(patient, patientDTO);
                        appointmentDTO.setPatient(patientDTO);
                    }

                    invoiceDTO.setAppointment(appointmentDTO);
                }

                return invoiceDTO;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean deleteInvoice(int id) {
        try {
            repo.delete(repo.findById(id).orElseThrow(() -> new NoSuchElementException("Invoice not found")));
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
            Invoice invoice = repo.findById(id).orElse(null);

            if (invoice != null) {
                // Copy properties from the InvoiceDTO to the Invoice entity
                BeanUtils.copyProperties(invoiceDTO, invoice, "id", "appointment");

                // Manually handle the Appointment and Patient
                if (invoiceDTO.getAppointment() != null) {
                    Appointment appointment = invoice.getAppointment();
                    BeanUtils.copyProperties(invoiceDTO.getAppointment(), appointment, "id", "patient");

                    if (invoiceDTO.getAppointment().getPatient() != null) {
                        Patient patient = appointment.getPatient();
                        BeanUtils.copyProperties(invoiceDTO.getAppointment().getPatient(), patient);
                        appointment.setPatient(patient);
                    }

                    invoice.setAppointment(appointment);
                }

                // Save the updated Invoice entity
                Invoice savedInvoice = repo.save(invoice);

                // Prepare the InvoiceDTO to return
                InvoiceDTO returnInvoiceDTO = new InvoiceDTO();
                BeanUtils.copyProperties(savedInvoice, returnInvoiceDTO);

                if (savedInvoice.getAppointment() != null) {
                    AppointmentDTO savedAppointmentDTO = new AppointmentDTO();
                    BeanUtils.copyProperties(savedInvoice.getAppointment(), savedAppointmentDTO);

                    if (savedInvoice.getAppointment().getPatient() != null) {
                        PatientDTO savedPatientDTO = new PatientDTO();
                        BeanUtils.copyProperties(savedInvoice.getAppointment().getPatient(), savedPatientDTO);
                        savedAppointmentDTO.setPatient(savedPatientDTO);
                    }

                    returnInvoiceDTO.setAppointment(savedAppointmentDTO);
                }

                return returnInvoiceDTO;
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

            // Check if the AppointmentDTO exists and map it
            if (invoiceDTO.getAppointment() != null) {
                Appointment appointment = new Appointment();
                BeanUtils.copyProperties(invoiceDTO.getAppointment(), appointment, "id", "patient");

                // Map the PatientDTO if it exists
                if (invoiceDTO.getAppointment().getPatient() != null) {
                    Patient patient = new Patient();
                    BeanUtils.copyProperties(invoiceDTO.getAppointment().getPatient(), patient);
                    appointment.setPatient(patient);
                }

                // Check if the appointment is new (ID is 0 or not provided), then save it
                if (appointment.getId() == 0) {
                    appointment = appointmentRepo.save(appointment);  // Save the appointment
                }

                invoice.setAppointment(appointment);  // Set the appointment to the invoice
            }

            // Copy other properties from InvoiceDTO to Invoice entity
            BeanUtils.copyProperties(invoiceDTO, invoice, "id", "appointment");

            // Save the Invoice entity
            Invoice savedInvoice = repo.save(invoice);

            // Prepare and return the saved InvoiceDTO
            InvoiceDTO returnInvoiceDTO = new InvoiceDTO();
            BeanUtils.copyProperties(savedInvoice, returnInvoiceDTO);

            // Map the AppointmentDTO to the returned InvoiceDTO
            if (savedInvoice.getAppointment() != null) {
                AppointmentDTO savedAppointmentDTO = new AppointmentDTO();
                BeanUtils.copyProperties(savedInvoice.getAppointment(), savedAppointmentDTO);

                if (savedInvoice.getAppointment().getPatient() != null) {
                    PatientDTO savedPatientDTO = new PatientDTO();
                    BeanUtils.copyProperties(savedInvoice.getAppointment().getPatient(), savedPatientDTO);
                    savedAppointmentDTO.setPatient(savedPatientDTO);
                }

                returnInvoiceDTO.setAppointment(savedAppointmentDTO);
            }

            return returnInvoiceDTO;

        } catch (Exception ex) {
            throw ex;
        }
    }


}
