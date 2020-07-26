package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.message.request.CreateHouseRequest;
import com.codegym.aribnb.message.response.CategoryList;
import com.codegym.aribnb.message.response.HouseDetail;
import com.codegym.aribnb.message.response.HouseList;
import com.codegym.aribnb.model.HouseEntity;
import com.codegym.aribnb.repository.IHouseRepository;
import com.codegym.aribnb.repository.dao.HouseDao;
import com.codegym.aribnb.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements IHouseService {
    @Autowired
    private IHouseRepository repository;

    @Autowired
    private HouseDao dao;

    @Override
    public List<HouseEntity> findByUser(Long userId) {
        return repository.findByUser(userId);
    }

    @Override
    public void createHouseRQ(CreateHouseRequest house) {
        HouseEntity houseEntity = house.cloneToEntity();
        dao.insert(houseEntity);
    }

    @Override
    public HouseDetail getHouseDetailById(Long id) {
        return dao.getHouseDetailById(id);
    }

    @Override
    public List<HouseList> getListHouse(int page, int pageSize) {
        return dao.getListHouse(page, pageSize);
    }

    @Override
    public List<CategoryList> getListCategory() {
        return dao.getListCategory();
    }

    @Override
    public List<HouseEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public HouseEntity findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void create(HouseEntity model) {
        repository.save(model);
    }

    @Override
    public void update(HouseEntity model) {
        repository.save(model);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
