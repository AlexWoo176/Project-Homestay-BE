package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.model.Rate;
import com.codegym.aribnb.repository.IRateRepository;
import com.codegym.aribnb.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    private IRateRepository repository;

    @Override
    public List<Rate> findAllByHouseId(Long houseId) {
        return this.repository.findAllByHouseId(houseId);
    }

    @Override
    public boolean existsRateByUserIdAndHouseId(Long id, Long houseId) {
        return this.repository.existsRateByUserIdAndHouseId(id, houseId);
    }

    @Override
    public Rate findByUserIdAndHouseId(Long userId, Long houseId) {
        return this.repository.findByUserIdAndHouseId(userId, houseId);
    }

    @Override
    public List<Rate> findAll() {
        return null;
    }

    @Override
    public Rate findById(Long id) {
        return null;
    }

    @Override
    public void create(Rate model) {
        this.repository.save(model);
    }

    @Override
    public void update(Rate model) {
        this.repository.save(model);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
