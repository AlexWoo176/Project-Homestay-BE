package com.codegym.aribnb.service;

import com.codegym.aribnb.message.response.OrderDetail;
import com.codegym.aribnb.message.response.UserOrderList;
import com.codegym.aribnb.message.response.UserOrderListOfHost;
import com.codegym.aribnb.model.OrderHouse;

import java.util.Date;
import java.util.List;

public interface IOrderHouseService extends IService<OrderHouse> {
    List<OrderHouse> findOrderHousesByTenantId(long id);

    List<OrderHouse> findOrderHousesByHouseId(long id);

    List<Long> getOrderIdsByHouseId(long id);

    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId);

    boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId);

    boolean existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(Date checkin, Date checkout,Long houseId);

    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout,Long houseId);

    boolean existsStatusHouseByStartDateGreaterThanEqualAndStartDateLessThanEqual(Date checkin, Date checkout, Long houseId);

    boolean existsStatusHouseByEndDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId);

    boolean existsStatusHouseByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date checkin, Date checkout, Long houseId);

    boolean existsStatusHouseByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId);

    List<UserOrderList> userOrderLists(Long userId);

    OrderDetail findById(Long userId, Long orderId);

    List<UserOrderListOfHost> userOrderListOfHost(Long hostId);
}
