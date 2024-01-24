package com.ced.app.socket.handler;

import com.ced.app.model.Utilisateur;
import com.ced.app.service.UtilisateurService;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        final String randomID = UUID.randomUUID().toString();
//        String bearerToken = request.getHeaders().getFirst("Authorization");

        String iduser = request.getURI().toString().split("idmessageuser=")[1];
        try{
//            String email = Utilisateur.extractEmail(Utilisateur.extractToken(bearerToken));
//            Utilisateur test = utilisateurService.getByEmail(email);
            //System.out.println("===========Niditra tato lery le !!!!!");
            LOG.info("User with ID '"+iduser+"' opened the page but this is the really id");
            return new UserPrincipal(iduser);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
