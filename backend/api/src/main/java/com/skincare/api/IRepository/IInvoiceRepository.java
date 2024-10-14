package com.skincare.api.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skincare.api.Entity.Invoice;


@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {

	
}
