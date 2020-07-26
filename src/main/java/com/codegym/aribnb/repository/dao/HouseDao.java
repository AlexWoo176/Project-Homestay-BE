package com.codegym.aribnb.repository.dao;

import com.codegym.aribnb.message.response.CategoryList;
import com.codegym.aribnb.message.response.HouseDetail;
import com.codegym.aribnb.message.response.HouseList;
import com.codegym.aribnb.message.response.HouseListOfHost;
import com.codegym.aribnb.model.HouseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class HouseDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(HouseEntity house) {
        em.persist(house);
    }

    public HouseDetail getHouseDetailById(Long houseId) {
        String sql = "select h.id, h.houseName, c.name catName,h.picture, h.address, h.bedroomNumber, h.bathroomNumber, h.description, h.price, h.area, h.status, u.name userName, u.id userId " +
                " from House h" +
                " LEFT JOIN users u" +
                " ON h.host_id = u.id " +
                " LEFT JOIN Category c " +
                " ON h.category = c.id" +
                " where h.id = :hid ";

        Query query = em.createNativeQuery(sql);
        query.setParameter("hid", houseId);
        Object[] result = (Object[]) query.getSingleResult();

        int i = 0;
        HouseDetail houseDetail = new HouseDetail();
        houseDetail.setId(Long.parseLong("" + result[i++]));
        houseDetail.setName("" + result[i++]);
        houseDetail.setCatName("" + result[i++]);
        houseDetail.setPicture("" + result[i++]);
        houseDetail.setAddress("" + result[i++]);
        houseDetail.setBedroomNumber("" + result[i++]);
        houseDetail.setBathroomNumber("" + result[i++]);
        houseDetail.setDescription("" + result[i++]);
        houseDetail.setPrice("" + result[i++]);
        houseDetail.setArea("" + result[i++]);
        houseDetail.setStatus("" + result[i++]);
        houseDetail.setUserName("" + result[i++]);
        houseDetail.setUserId("" + result[i++]);
        return houseDetail;
    }

    public List<HouseList> getListHouse(int page, int pageSize) {
        String sql = "select h.id, h.houseName,h.address, h.price, h.picture\n" +
                "from House h\n" +
                "left join users u\n" +
                "on h.host_id = u.id;";

        Query query = em.createNativeQuery(sql);
        List<Object[]> listResult = query.getResultList();

        List<HouseList> houseDetails = new ArrayList<>();
        HouseList item;
        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new HouseList();
            item.setId(Long.parseLong("" + row[i++]));
            item.setName(("" + row[i++]));
            item.setAddress(("" + row[i++]));
            item.setPrice(("" + row[i++]));
            item.setPicture("" + row[i++]);
            houseDetails.add(item);
        }
        return houseDetails;
    }

    public List<CategoryList> getListCategory() {
        String sql = "select c.id,c.name\n" +
                "from category c order by c.id ASC ";

        Query query = em.createNativeQuery(sql);
        List<Object[]> listResult = query.getResultList();
        List<CategoryList> categoryLists = new ArrayList<>();
        CategoryList item;
        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new CategoryList();
            item.setId(Long.parseLong("" + row[i++]));
            item.setName(("" + row[i++]));
            categoryLists.add(item);
        }
        return categoryLists;
    }

    public List<HouseListOfHost> getListHouseOfHost(Long userId) {
        String sql = "\n" +
                "                select h.id, h.houseName,h.address,c.name, h.price,h.status\n" +
                "                from House h \n" +
                "\t\t\t\tjoin users u\n" +
                "\t\t\t\tjoin category c\n" +
                "\t\t\t\tjoin user_roles ur\n" +
                "                on h.host_id = u.id and c.id = h.category and h.host_id = ur.user_id\n" +
                "                where ur.role_id = 1 and  ur.user_id = :urid  ;";

        Query query = em.createNativeQuery(sql);
        query.setParameter("urid", userId);

        List<Object[]> listResult = query.getResultList();


        List<HouseListOfHost> houseListOfHosts = new ArrayList<>();
        HouseListOfHost item;
        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new HouseListOfHost();
            item.setId(Long.parseLong("" + row[i++]));
            item.setName(("" + row[i++]));
            item.setAddress(("" + row[i++]));
            item.setCategoryName("" + row[i++]);
            item.setPrice(("" + row[i++]));
            item.setStatus("" + row[i++]);
            houseListOfHosts.add(item);
        }
        return houseListOfHosts;
    }

}
