package com.skincare.api.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skincare.api.Entity.Treatment;


@Repository
public interface ITreatmentRepository extends JpaRepository<Treatment, Integer> {

}
