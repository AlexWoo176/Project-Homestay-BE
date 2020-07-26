package com.codegym.aribnb.service;

import java.util.List;

public interface IService<T> {
    List<T> findAll();

    T findById(Long id);

    void create(T model);

    void update(T model);

    void delete(Long id);
}
