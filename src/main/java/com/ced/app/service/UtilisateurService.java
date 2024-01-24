package com.ced.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ced.app.model.Utilisateur;
import com.ced.app.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private UserRepository userRepository;

    public List<Utilisateur> getAllUser() {
        return userRepository.findAll();
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return userRepository.save(utilisateur);
    }

    public Utilisateur getByEmail(String email) {
        Optional<Utilisateur> optionalUser = userRepository.findByIdentifiant(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    public Utilisateur authenticateUser(String email, String password) {
        Optional<Utilisateur> optionalUser = userRepository.findByIdentifiant(email);
        Utilisateur toReturn = optionalUser.get();
        if (optionalUser.isPresent()) {
            System.out.println("REsult ============= "+toReturn.getIdentifiant()+" ============ "+toReturn.getMdp());
            System.out.println("================== The user is present is database");
            if (toReturn.getMdp().equals(password)){
                System.out.println("Utilisateur authentifie");
                return optionalUser.get();
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean isTokenValid(String token) throws Exception{
        boolean toReturn = false;
            try {
                Claims cl = Utilisateur.extractClaims(token);
                if(cl != null){
                    if (!cl.getExpiration().before(new Date(System.currentTimeMillis()))) {
                        toReturn = true;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        return toReturn;
    }
}
