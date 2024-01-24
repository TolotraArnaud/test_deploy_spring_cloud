package com.ced.app.repository;

import com.ced.app.model.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.Integer;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByIdentifiant(String identifiant);
}
