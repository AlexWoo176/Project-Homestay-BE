package com.codegym.aribnb.service;

import com.codegym.aribnb.message.response.CommentList;
import com.codegym.aribnb.model.Comment;

import java.util.List;

public interface ICommentService extends IService<Comment> {
    List<CommentList> findAllByHouseId(Long houseId);

}
