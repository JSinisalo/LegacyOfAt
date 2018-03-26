package com.hert.legacyofatbackend.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static java.util.Collections.emptyList;

public class GoogleAuthorizationService {

    public static Authentication getAuthentication(HttpServletRequest request) {

        GoogleIdToken token = null;

        if(request.getHeader(SecurityConstants.HEADER_STRING) != null)
            token = verifyGoogleIdToken(request.getHeader(SecurityConstants.HEADER_STRING).replace("Bearer ", ""));

        if(token != null) {

            //TODO: gets all roles maybe change later?
            return new UsernamePasswordAuthenticationToken(token.getPayload().getSubject(), null, emptyList());
        }

        System.out.println("Illegal aliens tried to contact the server " + request);

        return null;
    }

    private static GoogleIdToken verifyGoogleIdToken(String token) {

        GoogleIdToken idToken = null;

        if(token == null)
            return null;

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList(SecurityConstants.CLIENT_ID))
                .setIssuer(SecurityConstants.ISSUER)
                .build();

        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return idToken;
    }
}
