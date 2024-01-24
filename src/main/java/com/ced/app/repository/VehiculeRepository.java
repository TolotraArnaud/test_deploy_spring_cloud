package com.ced.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.Integer;
import com.ced.app.model.Vehicule;


public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    
}
