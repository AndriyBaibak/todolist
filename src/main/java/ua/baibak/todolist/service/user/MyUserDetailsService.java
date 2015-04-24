package ua.baibak.todolist.service.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class MyUserDetailsService implements UserDetailsService {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MyUserDetailsService.class);
    @Inject
    private UserEntityService userService;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
            ua.baibak.todolist.entity.User user = null;
        try {
            user = userService.findUserByName(username);
        } catch (Exception e) {
            log.error(e.toString());
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User userDetail = new User(user.getUserName(), user.getPassword(), authorities);
        return userDetail;
    }
}