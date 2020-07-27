package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.model.Role;
import com.codegym.aribnb.model.RoleName;
import com.codegym.aribnb.repository.IRoleRepository;
import com.codegym.aribnb.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository repository;


    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return repository.findByName(roleName);
    }
}
