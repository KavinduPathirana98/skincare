package com.skincare.api.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skincare.api.Entity.Patient;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

}
