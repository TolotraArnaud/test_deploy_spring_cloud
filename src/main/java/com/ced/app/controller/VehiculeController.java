package com.ced.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.ced.app.service.VehiculeService;

import java.util.List;
import com.ced.app.model.Vehicule;

@RestController
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeservice;


    @GetMapping("/vehicules")
	public List<Vehicule> getAllVehicules(){
		return vehiculeservice.getAllVehicules();
	}
}
