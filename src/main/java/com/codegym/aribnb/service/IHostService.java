package com.codegym.aribnb.service;

import com.codegym.aribnb.message.response.HouseListOfHost;

import java.util.List;

public interface IHostService {
    List<HouseListOfHost> getHouseListOfHosts(Long userId);
}
