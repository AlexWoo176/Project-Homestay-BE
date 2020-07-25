package com.codegym.aribnb.repository;

import com.codegym.aribnb.model.ImageOfHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageHouseRepository extends JpaRepository<ImageOfHouse, Long> {
    List<ImageOfHouse> findByHouseId(Long id);
}
