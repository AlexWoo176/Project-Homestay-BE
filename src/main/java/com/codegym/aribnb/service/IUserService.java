package com.codegym.aribnb.service;

import com.codegym.aribnb.message.response.UserInformation;
import com.codegym.aribnb.model.User;

public interface IUserService extends IService<User> {
    User findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    UserInformation findByIdCurrent(Long id);
}
