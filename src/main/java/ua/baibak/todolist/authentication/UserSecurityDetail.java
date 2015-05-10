package ua.baibak.todolist.authentication;

import org.springframework.security.core.GrantedAuthority;
import ua.baibak.todolist.entity.User;

import java.util.Collection;


public class UserSecurityDetail extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private User info;

    public UserSecurityDetail(User info, String username,
                              String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.info = info;
    }

    public UserSecurityDetail(String username, String password,
                              boolean enabled, boolean accountNonExpired,
                              boolean credentialsNonExpired, boolean accountNonLocked,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserSecurityDetail(String username, String password,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public User getInfo() {
        return info;
    }

    public void setInfo(User info) {
        this.info = info;
    }
}

