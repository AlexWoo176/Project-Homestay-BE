package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.message.response.OrderDetail;
import com.codegym.aribnb.message.response.UserOrderList;
import com.codegym.aribnb.message.response.UserOrderListOfHost;
import com.codegym.aribnb.model.OrderHouse;
import com.codegym.aribnb.model.StatusHouse;
import com.codegym.aribnb.repository.IOrderHouseRepository;
import com.codegym.aribnb.repository.IStatusHouseRepository;
import com.codegym.aribnb.repository.dao.OrderDao;
import com.codegym.aribnb.service.IOrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderHouseServiceImpl implements IOrderHouseService {
    @Autowired
    private OrderDao dao;

    @Autowired
    private IOrderHouseRepository orderHouseRepository;

    @Autowired
    private IStatusHouseRepository statusHouseRepository;

    @Override
    public List<OrderHouse> findOrderHousesByTenantId(long id) {
        return this.orderHouseRepository.findOrderHousesByTenantId(id);
    }

    @Override
    public List<OrderHouse> findOrderHousesByHouseId(long id) {
        return this.orderHouseRepository.findOrderHousesByHouseId(id);
    }

    @Override
    public List<Long> getOrderIdsByHouseId(long id) {
        List<Long> listOrderId = new ArrayList<>();
        List<OrderHouse> orderHouses = orderHouseRepository.findOrderHousesByHouseId(id);
        for (OrderHouse orderHouse : orderHouses) {
            listOrderId.add(orderHouse.getId());
        }
        return listOrderId;
    }

    @Override
    public boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsStatusHouseByStartDateGreaterThanEqualAndStartDateLessThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (StatusHouse statusHouse : statusHouses) {
            boolean startIn = statusHouse.getStartDate().after(checkin);
            boolean startOut = statusHouse.getStartDate().before(checkout);
            if (startIn && startOut) {
                return true;
            }
            ;
        }
        return false;
    }

    @Override
    public boolean existsStatusHouseByEndDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (StatusHouse statusHouse : statusHouses) {
            boolean endIn = statusHouse.getEndDate().after(checkin);
            boolean endOut = statusHouse.getEndDate().before(checkout);
            if (endIn && endOut) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsStatusHouseByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (StatusHouse statusHouse : statusHouses) {
            boolean startIn = statusHouse.getStartDate().before(checkin);
            boolean endOut = statusHouse.getEndDate().after(checkout);
            if (startIn && endOut) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsStatusHouseByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (StatusHouse statusHouse : statusHouses) {
            boolean startIn = statusHouse.getStartDate().after(checkin);
            boolean endOut = statusHouse.getEndDate().before(checkout);
            if (startIn && endOut) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserOrderList> userOrderLists(Long userId) {
        return dao.userOrderLists(userId);
    }

    @Override
    public OrderDetail findById(Long userId, Long orderId) {
        return dao.orderDetail(userId, orderId);
    }

    @Override
    public List<UserOrderListOfHost> userOrderListOfHost(Long hostId) {
        return dao.userOrderListOfHost(hostId);
    }

    @Override
    public List<OrderHouse> findAll() {
        return this.orderHouseRepository.findAll();
    }

    @Override
    public OrderHouse findById(Long id) {
        return this.orderHouseRepository.findById(id).get();
    }

    @Override
    public void create(OrderHouse model) {
        this.orderHouseRepository.save(model);
    }

    @Override
    public void update(OrderHouse model) {
        this.orderHouseRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        this.orderHouseRepository.deleteById(id);
    }
}
