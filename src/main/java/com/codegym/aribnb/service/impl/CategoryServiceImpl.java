package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.model.Category;
import com.codegym.aribnb.repository.ICategoryRepository;
import com.codegym.aribnb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository repository;


    @Override
    public Category findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void create(Category model) {
        repository.save(model);
    }

    @Override
    public void update(Category model) {
        repository.save(model);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
