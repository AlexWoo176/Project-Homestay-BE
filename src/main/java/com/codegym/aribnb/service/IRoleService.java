package com.codegym.aribnb.service;

import com.codegym.aribnb.model.Role;
import com.codegym.aribnb.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleName);
}
