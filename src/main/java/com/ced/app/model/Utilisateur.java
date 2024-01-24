package com.ced.app.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpHeaders;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Column(name="identifiant", unique = true)
    private String identifiant;
    private String mdp;
    @ManyToOne
    @JoinColumn(name="idUserStatus")
    private UserStatus userStatus;

    @Transient
    @Value("${jwt.secret}")
    private String secret;

    public Utilisateur(String nom, String identifiant, String mdp) {
        this.setNom(nom);
        this.setIdentifiant(identifiant);
        this.setMdp(mdp);
    }

    public Utilisateur() {
    }

    public String generateToken(Utilisateur user) {
        // Create JWT token using the user information
        // Set expiration, claims, etc. as needed
        // Sign the token with the secret key
        return Jwts.builder()
                .setSubject("data user")
                .claim("identifiant",user.getIdentifiant())
                .claim("nom",user.getNom())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days validity
                .signWith(SignatureAlgorithm.HS512, "mytoken00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")
                .compact();
    }

    public static boolean isTokenValid(String token) throws Exception{
        boolean toReturn = false;
        try {
            Claims cl = Utilisateur.extractClaims(token);
            if(cl != null){
                if (cl.get("identifiant")!= null && cl.get("nom") != null && cl.get("exp") != null){
                    toReturn = true;
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return toReturn;
    }

    public static boolean isBearerTokenValid(String token) throws Exception{
        boolean toReturn = false;
        System.out.println("Bearer token = "+token);
        try {
            if (token.startsWith("Bearer ")){
                String temp_token = token.substring(7);
                System.out.println(temp_token);
                toReturn = Utilisateur.isTokenValid(temp_token);
            } else {
                System.out.println("Bearer token not found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return toReturn;
    }

    public static boolean isTokenExpired(String token) throws Exception{
        boolean toReturn = false;
        System.out.println("Bearer token = "+token);
        try {
            Claims cl = Utilisateur.extractClaims(token);
            if(cl != null){
                System.out.println("Exp : "+cl.getExpiration()+" Now :" +new java.util.Date(System.currentTimeMillis()));
                if (!cl.getExpiration().before(new java.util.Date(System.currentTimeMillis()))) {
                    toReturn = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return toReturn;
    }

    public static String extractUsername(String token){
        return (String) Utilisateur.extractClaims(token).get("nom");
    }

    public static String extractEmail(String token){
        return (String) Utilisateur.extractClaims(token).get("identifiant");
    }

    public static String extractToken(String bearerToken) throws Exception{
        if (bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        } else {
            throw new Exception("Token invalide");
        }
    }

    public static Claims extractClaims(String token) {
        // Extraire les revendications (claims) du token
        return Jwts.parser().setSigningKey("mytoken00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000").parseClaimsJws(token).getBody();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }


}
