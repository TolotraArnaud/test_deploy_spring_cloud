package com.ced.app.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

// import com.nante.app.crud.service.GenericService;
import com.ced.app.model.Vehicule;
import com.ced.app.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

@Service
public class VehiculeService {
    @Autowired
    private VehiculeRepository vehiculeRepository;

    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }
}
