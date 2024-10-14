package com.skincare.api.IService;

import java.util.List;


import com.skincare.api.DTO.InvoiceDTO;

public interface IInvoiceService {

    List<InvoiceDTO> getAllInvoices();
    InvoiceDTO getInvoiceById(int id);
    boolean deleteInvoice(int id);
    InvoiceDTO updateInvoice(int id,InvoiceDTO invoiceDTO);
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);
}
