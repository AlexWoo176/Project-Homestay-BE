package com.codegym.aribnb.service;


import com.codegym.aribnb.model.Category;

public interface ICategoryService extends IService<Category> {
    Category findByName(String name);

}
