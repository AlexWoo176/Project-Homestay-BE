package com.codegym.aribnb.security.services;

import com.codegym.aribnb.message.request.SignUpForm;
import com.codegym.aribnb.model.User;
import com.codegym.aribnb.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        return UserPrinciple.build(user);
    }
    public User save(SignUpForm userForm) {
        User newUser = new User();
        newUser.setUsername(userForm.getUsername());
        newUser.setPassword(passwordEncoder.encode(userForm.getPassword()));
        return repository.save(newUser);
    }
}
