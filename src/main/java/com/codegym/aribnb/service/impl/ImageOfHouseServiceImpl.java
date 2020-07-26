package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.model.ImageOfHouse;
import com.codegym.aribnb.repository.IImageHouseRepository;
import com.codegym.aribnb.service.IImageHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageOfHouseServiceImpl implements IImageHouseService {
    @Autowired
    private IImageHouseRepository repository;

    @Override
    public List<ImageOfHouse> findByHouseId(Long id) {
        return repository.findByHouseId(id);
    }

    @Override
    public List<String> getListImageUrlOfHouseByHouseId(Long id) {
        List<String> listImageUrl = new ArrayList<>();
        List<ImageOfHouse> imageOfHouses = repository.findByHouseId(id);
        for (ImageOfHouse image : imageOfHouses) {
            listImageUrl.add(image.getImageUrl());
        }
        return listImageUrl;
    }

    @Override
    public List<ImageOfHouse> findAll() {
        return repository.findAll();
    }

    @Override
    public ImageOfHouse findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void create(ImageOfHouse model) {
        repository.save(model);
    }

    @Override
    public void update(ImageOfHouse model) {
        repository.save(model);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
