package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.model.StatusHouse;
import com.codegym.aribnb.repository.IStatusHouseRepository;
import com.codegym.aribnb.service.IStatusHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusHouseServiceImpl implements IStatusHouseService {
    @Autowired
    private IStatusHouseRepository repository;

    @Override
    public List<StatusHouse> findAllByHouseId(Long houseId) {
        return this.repository.findAllByHouseId(houseId);
    }

    @Override
    public List<StatusHouse> findAll() {
        return this.repository.findAll();
    }

    @Override
    public StatusHouse findById(Long id) {
        return this.repository.findById(id).get();
    }

    @Override
    public void create(StatusHouse model) {
        this.repository.save(model);
    }

    @Override
    public void update(StatusHouse model) {
        this.repository.save(model);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
