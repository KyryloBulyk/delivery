package com.delivery.restaurant.security;

import com.delivery.restaurant.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OurUserDetailService implements UserDetailsService {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("12345"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("Kyrylo")
                .password(encoder.encode("1234567"))
                .roles("USER")
                .build();

        if(username.equals(admin.getUsername())) {
            return admin;
        } else if(username.equals(user.getUsername())) {
            return user;
        }

        return null;
    }
}
