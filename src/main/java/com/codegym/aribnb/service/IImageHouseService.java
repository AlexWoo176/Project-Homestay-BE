package com.codegym.aribnb.service;

import com.codegym.aribnb.model.ImageOfHouse;

import java.util.List;

public interface IImageHouseService extends IService<ImageOfHouse> {
    List<ImageOfHouse> findByHouseId(Long id);

    List<String> getListImageUrlOfHouseByHouseId(Long id);

}
