package com.codegym.aribnb.repository;

import com.codegym.aribnb.model.OrderHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IOrderHouseRepository extends JpaRepository<OrderHouse, Long> {
    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId);

    boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId);

    boolean existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(Date checkin, Date checkout, Long houseId);

    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId);

    List<OrderHouse> findOrderHousesByTenantId(long id);

    List<OrderHouse> findOrderHousesByHouseId(long id);
}
