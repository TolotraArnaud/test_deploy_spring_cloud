package com.ced.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    // @ManyToMany(mappedBy = "materiaux")
    // private List<Style> styles;
    
    public UserStatus(String description) {
        this.setDescription(description);
    }
    public UserStatus() {
    }
    // public List<Style> getStyles() {
    //     return styles;
    // }
    // public void setStyles(List<Style> styles) {
    //     this.styles = styles;
    // }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
