package ua.baibak.todolist.service.user.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthenticationUtils {


    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication loginUser(String userName, String password) {
        Authentication authentication = null;

        authentication = tryToAuthenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private Authentication tryToAuthenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userName, password);
        Authentication auth = authenticationManager.authenticate(token);
        return auth;
    }
}
