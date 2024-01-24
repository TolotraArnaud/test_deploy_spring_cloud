package com.ced.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import java.sql.Date;
import java.util.List;

import com.ced.app.model.Modele;
import com.ced.app.model.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;


@Entity
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date_fabrication;
    private String couleur;
    @ManyToOne
    @JoinColumn(name="idModele")
    private Modele modele;
    @ManyToOne
    @JoinColumn(name="idTransmission")
    private Transmission transmission;
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kilometrage> kilometrages;

    public Vehicule(Date date_fabrication, String couleur) {
        this.setDate_fabrication(date_fabrication);
        this.setCouleur(couleur);
    }
    public Vehicule() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate_fabrication() {
        return date_fabrication;
    }
    public void setDate_fabrication(Date date_fabrication) {
        this.date_fabrication = date_fabrication;
    }
    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    public Modele getModele() {
        return modele;
    }
    public void setModele(Modele modele) {
        this.modele = modele;
    }
    public Transmission getTransmission() {
        return transmission;
    }
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
    public List<Kilometrage> getKilometrages() {
        return kilometrages;
    }
    public void setKilometrages(List<Kilometrage> kilometrages) {
        this.kilometrages = kilometrages;
    }

    
}
