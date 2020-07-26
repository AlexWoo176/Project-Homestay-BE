package com.codegym.aribnb.service;

import com.codegym.aribnb.model.Rate;

import java.util.List;

public interface IRateService extends IService<Rate> {
    List<Rate> findAllByHouseId(Long houseId);

    boolean existsRateByUserIdAndHouseId (Long id, Long houseId);

    Rate findByUserIdAndHouseId(Long userId, Long houseId);
}
