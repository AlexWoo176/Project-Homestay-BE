package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.message.response.UserInformation;
import com.codegym.aribnb.model.User;
import com.codegym.aribnb.repository.IUserRepository;
import com.codegym.aribnb.repository.dao.UserDao;
import com.codegym.aribnb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserDao dao;

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public UserInformation findByIdCurrent(Long id) {
        return dao.userInformation(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void create(User model) {
        repository.save(model);
    }

    @Override
    public void update(User model) {
        repository.save(model);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
