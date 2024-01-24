package com.ced.app.controller;

import com.ced.app.model.Message;
import com.ced.app.model.Utilisateur;
import com.ced.app.model.auth.Token;
import com.ced.app.socket.service.WebSocketService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ced.app.service.UtilisateurService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private UtilisateurService userservice;

    @GetMapping
    public List<Utilisateur> getAllUser(){
        return userservice.getAllUser();
    }

    @PostMapping("/login")
    public Token checklogin(@RequestBody Map<String, String> data) throws Exception{
        System.out.println("Identifiant :"+data.get("identifiant")+" Mdp:"+data.get("mdp"));
        String identifiant = data.get("identifiant");
        String mdp = data.get("mdp");
        if (data.get("message")!= null){
            System.out.println(data.get("message"));
        }
        Utilisateur authenticatedUser = userservice.authenticateUser(identifiant, mdp);

        if (authenticatedUser != null) {
            // Return successful authentication response
            System.out.println("User is authentified");
            String token = new Utilisateur().generateToken(authenticatedUser);
            Token t = new Token(token);
            System.out.println("User is authenticaded : "+authenticatedUser);
            System.out.println("Token : "+token);
            System.out.println("My token when decoded : "+Utilisateur.extractClaims(token));
            return t;
        } else {
            // Return unsuccessful authentication response
            throw new Exception("Identifiant non reconnu");
        }
    }

    @PostMapping
    public Token register(@RequestBody Map<String, String> data) throws Exception{
        String nom = data.get("nom");
        String identifiant = data.get("identifiant");
        String mdp = data.get("mdp");
        Utilisateur user = new Utilisateur(nom, identifiant, mdp);
        userservice.saveUtilisateur(user);
        Map<String, String> log = new HashMap<>();
        log.put("identifiant", user.getIdentifiant());
        log.put("mdp", user.getMdp());
        Token token = checklogin(log);
        return token;

    }

}
