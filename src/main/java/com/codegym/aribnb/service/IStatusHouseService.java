package com.codegym.aribnb.service;

import com.codegym.aribnb.model.StatusHouse;

import java.util.List;

public interface IStatusHouseService extends IService<StatusHouse> {
    List<StatusHouse> findAllByHouseId(Long houseId);
}
