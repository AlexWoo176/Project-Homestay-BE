package com.codegym.aribnb.service.impl;

import com.codegym.aribnb.message.response.HouseListOfHost;
import com.codegym.aribnb.repository.dao.HouseDao;
import com.codegym.aribnb.service.IHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostServiceImpl implements IHostService {
    @Autowired
    private HouseDao dao;

    @Override
    public List<HouseListOfHost> getHouseListOfHosts(Long userId) {
        return dao.getListHouseOfHost(userId);
    }
}
