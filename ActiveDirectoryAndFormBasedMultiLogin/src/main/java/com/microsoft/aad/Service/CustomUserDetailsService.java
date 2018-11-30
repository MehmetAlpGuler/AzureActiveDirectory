package com.microsoft.aad.Service;

import com.microsoft.aad.dto.User;
import com.microsoft.aad.dto.SpringUserDetailsImpl;
import com.microsoft.aad.util.GetPropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MehmetAlpGuler
 */

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private GetPropertyValues getPropertyValues;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);

        return createUser(user);
    }


    private SpringUserDetailsImpl createUser(User user) {
        SpringUserDetailsImpl ud = null;
        if (user != null) {
            ud = new SpringUserDetailsImpl();
            ud.setUsername(user.getUsername());
            ud.setPassword(user.getPassword());
            ud.setFirstname(user.getFirstName());
            ud.setLastname(user.getLastName());
            ud.setEnabled(user.isEnabled());
            ud.setAuthorities(getAuthorities(user.getRoles()));
        }
        return ud;
    }


    public Collection<? extends GrantedAuthority> getAuthorities(List<String> userRoles) {
        return getGrantedAuthorities(userRoles);
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
