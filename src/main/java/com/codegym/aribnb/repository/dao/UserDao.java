package com.codegym.aribnb.repository.dao;

import com.codegym.aribnb.message.response.UserInformation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public UserInformation userInformation(Long userId) {
        String sql = "select u.id,u.email,u.name,u.username,u.picture,r.name roleName\n" +
                "from users u\n" +
                "join  airbnb.user_roles ur\n" +
                "on ur.user_id = u.id\n" +
                "join roles r\n" +
                "on r.id =  ur.role_id\n" +
                "where u.id = :userId";

        Query query = em.createNativeQuery(sql);
        query.setParameter("userId", userId);
        Object[] result = (Object[]) query.getSingleResult();

        int i = 0;

        UserInformation userInformation = new UserInformation();
        userInformation.setId(Long.parseLong("" + result[i++]));
        userInformation.setEmail("" + result[i++]);
        userInformation.setName("" + result[i++]);
        userInformation.setUsername("" + result[i++]);
        userInformation.setPicture("" + result[i++]);
        userInformation.setRole_name("" + result[i++]);
        return userInformation;
    }
}
