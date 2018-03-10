package com.rebirthlab.gradebook.auth.service;

import com.rebirthlab.gradebook.auth.model.User;
import com.rebirthlab.gradebook.auth.model.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by Anastasiy
 */
@Component
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(email);
        return user.orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
