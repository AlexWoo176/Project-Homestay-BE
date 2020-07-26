package com.codegym.aribnb.service;

import com.codegym.aribnb.message.request.CreateHouseRequest;
import com.codegym.aribnb.message.response.CategoryList;
import com.codegym.aribnb.message.response.HouseDetail;
import com.codegym.aribnb.message.response.HouseList;
import com.codegym.aribnb.model.HouseEntity;

import java.util.List;

public interface IHouseService extends IService<HouseEntity> {
    List<HouseEntity> findByUser(Long userId);

    void createHouseRQ(CreateHouseRequest house);

    HouseDetail getHouseDetailById(Long id);

    List<HouseList> getListHouse(int page, int pageSize);

    List<CategoryList> getListCategory();
}
