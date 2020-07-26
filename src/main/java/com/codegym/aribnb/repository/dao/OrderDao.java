package com.codegym.aribnb.repository.dao;

import com.codegym.aribnb.message.response.OrderDetail;
import com.codegym.aribnb.message.response.UserOrderList;
import com.codegym.aribnb.message.response.UserOrderListOfHost;
import com.codegym.aribnb.model.HouseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class OrderDao {
    @PersistenceContext
    private EntityManager em;

    public void insert(HouseEntity house) {
        em.persist(house);
    }

    String pattern = "MM/dd/yyyy HH:mm:ss";

    DateFormat df = new SimpleDateFormat(pattern);

    public List<UserOrderList> userOrderLists(Long userId) {
        String sql = "select oh.id, oh.checkin, oh.checkout, oh.numberGuest, oh.children, oh.orderTime, oh.house_id, h.houseName\n" +
                "from orderhouse oh \n" +
                "join users u\n" +
                "join house h\n" +
                "join user_roles r\n" +
                "on oh.house_id = h.id and u.id = oh.tenant and u.id = r.user_id\n" +
                "where r.role_id = 2 and u.id = :uid order by oh.id ASC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("uid", userId);

        List<Object[]> listResult = query.getResultList();


        List<UserOrderList> userOrderLists = new ArrayList<>();
        UserOrderList item;
        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new UserOrderList();
            item.setId(Long.parseLong("" + row[i++]));
            item.setCheckin(" " + row[i++]);
            item.setCheckout(" " + row[i++]);
            item.setNumberGuest(Long.parseLong(("" + row[i++])));
            item.setChildren(Long.parseLong("" + row[i++]));
            item.setOrderTime(" " + row[i++]);
            item.setHouse_id(Long.parseLong("" + row[i++]));
            item.setHouseName(" " + row[i++]);
            userOrderLists.add(item);
        }
        return userOrderLists;
    }

    public OrderDetail orderDetail(Long userId, Long orderId) {
        String sql = "select oh.id, oh.checkin, oh.checkout, oh.numberGuest, oh.children, oh.orderTime, oh.house_id, h.houseName\n" +
                "from orderhouse oh \n" +
                "join users u\n" +
                "join house h\n" +
                "join user_roles r\n" +
                "on oh.house_id = h.id and u.id = oh.tenant and u.id = r.user_id\n" +
                "where r.role_id = 2 and u.id = :uid and oh.id = :ohid";

        Query query = em.createNativeQuery(sql);
        query.setParameter("uid", userId);
        query.setParameter("ohid", orderId);

        Object[] result = (Object[]) query.getSingleResult();

        List<UserOrderList> userOrderLists = new ArrayList<>();
        UserOrderList item;
        int i = 0;

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(Long.parseLong("" + result[i++]));
        orderDetail.setCheckin("" + result[i++]);
        orderDetail.setCheckout("" + result[i++]);
        orderDetail.setNumberGuest(Long.parseLong("" + result[i++]));
        orderDetail.setChildren(Long.parseLong("" + result[i++]));
        orderDetail.setOrderTime("" + result[i++]);
        orderDetail.setHouse_id(Long.parseLong("" + result[i++]));
        orderDetail.setHouseName("" + result[i++]);

        return orderDetail;
    }

    public List<UserOrderListOfHost> userOrderListOfHost(Long hostId) {
        String sql = "                select oh.id, oh.checkin, oh.checkout, oh.numberGuest,\n" +
                " oh.children, oh.orderTime, oh.house_id, h.houseName\n" +
                "                from orderhouse oh \n" +
                "                join users u\n" +
                "                join house h\n" +
                "                join user_roles r\n" +
                "                on oh.house_id = h.id and u.id = oh.tenant and u.id = r.user_id\n" +
                "                where r.role_id = 2 and h.host_id = :hid order by oh.id ASC;";

        Query query = em.createNativeQuery(sql);
        query.setParameter("hid", hostId);

        List<Object[]> listResult = query.getResultList();


        List<UserOrderListOfHost> userOrderListOfHosts = new ArrayList<>();
        UserOrderListOfHost item;
        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new UserOrderListOfHost();
            item.setId(Long.parseLong("" + row[i++]));
            item.setCheckin(" " + row[i++]);
            item.setCheckout(" " + row[i++]);
            item.setNumberGuest(Long.parseLong(("" + row[i++])));
            item.setChildren(Long.parseLong("" + row[i++]));
            item.setOrderTime(" " + row[i++]);
            item.setHouse_id(Long.parseLong("" + row[i++]));
            item.setHouseName(" " + row[i++]);
            userOrderListOfHosts.add(item);
        }
        return userOrderListOfHosts;
    }

}
