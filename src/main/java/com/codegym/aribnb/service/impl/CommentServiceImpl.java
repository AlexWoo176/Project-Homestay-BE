package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.message.response.CommentList;
import com.codegym.aribnb.model.Comment;
import com.codegym.aribnb.repository.ICommentRepository;
import com.codegym.aribnb.repository.dao.CommentDao;
import com.codegym.aribnb.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository repository;

    @Autowired
    private CommentDao dao;

    @Override
    public List<CommentList> findAllByHouseId(Long houseId) {
        return dao.getListComment(houseId);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void create(Comment model) {
        this.repository.save(model);
    }

    @Override
    public void update(Comment model) {
        this.repository.save(model);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
