package com.codegym.aribnb.repository;

import com.codegym.aribnb.message.response.CommentList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class CommentDao {
    @PersistenceContext
    private EntityManager em;

    public List<CommentList> getListComment(Long houseId) {
        String sql = "select c.id,c.comment,c.house,c.user,h.houseName houseName,u.name userName \n" +
                "from comment c\n" +
                "join house h \n" +
                "on h.id = c.house\n" +
                "join users u \n" +
                "on u.id = c.user\n" +
                "where h.id =:hid order by c.id DESC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("hid", houseId);

        List<Object[]> listResult = query.getResultList();

        List<CommentList> commentLists = new ArrayList<>();
        CommentList item;
        int i;
        for (Object[] row : listResult) {
            i = 0;
            item = new CommentList();
            item.setId(Long.parseLong("" + row[i++]));
            item.setComment(("" + row[i++]));
            item.setHouseId(Long.parseLong("" + row[i++]));
            item.setUserId(Long.parseLong("" + row[i++]));
            item.setHouseName(("" + row[i++]));
            item.setUserName(("" + row[i++]));
            commentLists.add(item);
        }
        return commentLists;
    }
}
