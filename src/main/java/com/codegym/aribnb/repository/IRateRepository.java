package com.codegym.aribnb.repository;

import com.codegym.aribnb.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByHouseId(Long id);

    boolean existsRateByUserIdAndHouseId (Long id, Long houseId);

    Rate findByUserIdAndHouseId(Long userId, Long houseId);
}
