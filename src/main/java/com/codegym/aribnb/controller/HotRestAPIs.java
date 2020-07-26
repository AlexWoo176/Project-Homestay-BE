package com.codegym.aribnb.controller;

import com.codegym.aribnb.message.request.CreateHouseRequest;
import com.codegym.aribnb.message.response.HouseListOfHost;
import com.codegym.aribnb.message.response.ResponseMessage;
import com.codegym.aribnb.message.response.UserOrderListOfHost;
import com.codegym.aribnb.model.HouseEntity;
import com.codegym.aribnb.model.StatusHouse;
import com.codegym.aribnb.security.services.UserPrinciple;
import com.codegym.aribnb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/host")
public class HotRestAPIs {
    public static final String FAIL_NOT_FOUND = "Fail. Not found";
    public static final String GET_THE_STATUS_HOUSE_SUCCESSFULLY = "Get the status house successfully";
    public static final String UPDATE_THE_STATUS_HOUSE_SUCCESSFULLY = "Update the status house successfully";
    public static final String POST_A_NEW_STATUS_HOUSE_SUCCESSFULLY = "Post a new status house successfully";
    public static final String DELETE_THE_STATUS_HOUSE_SUCCESSFULLY = "Delete the status house successfully";
    public static final String SUCCESSFULLY_GET_LIST_HOUSE_OF_HOST = "Successfully. Get list house of host";
    public static final String POST_A_NEW_HOUSE_SUCCESSFULLY = "Post a new house successfully";
    public static final String UPDATE_SUCCESSFULLY = "Update successfully";
    public static final String DELETE_THE_HOUSE_SUCCESSFULLY = "Delete the house successfully";
    public static final String LIST_ALL_ORDER = "list all order";
    @Autowired
    private IHostService hostService;

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IOrderHouseService orderHouseService;

    @Autowired
    private IStatusHouseService statusHouseService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/statusHouses/{id}")
    public ResponseEntity<ResponseMessage> getStatusHouseById(@PathVariable Long id) {
        StatusHouse statusHouse = this.statusHouseService.findById(id);

        if (statusHouse == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, GET_THE_STATUS_HOUSE_SUCCESSFULLY, statusHouse),
                HttpStatus.OK);
    }

    @PutMapping("/statusHouses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> editStatusHouse(@RequestBody StatusHouse statusHouse, @PathVariable Long id) {
        StatusHouse currentStatusHouse = this.statusHouseService.findById(id);

        if (currentStatusHouse == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND);
        }

        currentStatusHouse.setStartDate(statusHouse.getStartDate());
        currentStatusHouse.setEndDate(statusHouse.getEndDate());

        this.statusHouseService.create(currentStatusHouse);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, UPDATE_THE_STATUS_HOUSE_SUCCESSFULLY, null),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/statusHouses")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> createStatusHouse(@RequestBody StatusHouse statusHouse) {
        this.statusHouseService.create(statusHouse);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, POST_A_NEW_STATUS_HOUSE_SUCCESSFULLY, null),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/statusHouses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> deleteStatusHouse(@PathVariable Long id) {
        StatusHouse statusHouse = this.statusHouseService.findById(id);

        if (statusHouse == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND);
        }

        this.statusHouseService.delete(id);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, DELETE_THE_STATUS_HOUSE_SUCCESSFULLY, null),
                HttpStatus.OK);
    }

    @GetMapping("/houses")
    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> listHouseOfHost() {
        long userId = getCurrentUser().getId();
        List<HouseListOfHost> houses = hostService.getHouseListOfHosts(userId);
        if (houses.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_HOUSE_OF_HOST, houses),
                HttpStatus.OK);
    }

    @PostMapping("/houses")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> createHouse(@RequestBody CreateHouseRequest house) {
        houseService.createHouseRQ(house);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, POST_A_NEW_HOUSE_SUCCESSFULLY, null),
                HttpStatus.CREATED);
    }

    @PutMapping("/houses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> editHouse(@RequestBody HouseEntity house, @PathVariable Long id) {
        HouseEntity currentHouse = this.houseService.findById(id);
        if (currentHouse == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND);
        }
        currentHouse.setHouseName(house.getHouseName());
        currentHouse.setCategory(house.getCategory());
        currentHouse.setPicture(house.getPicture());
        currentHouse.setAddress(house.getAddress());
        currentHouse.setBedroomNumber(house.getBedroomNumber());
        currentHouse.setBathroomNumber(house.getBathroomNumber());
        currentHouse.setDescription(house.getDescription());
        currentHouse.setPrice(house.getPrice());
        currentHouse.setArea(house.getArea());

        this.houseService.update(currentHouse);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, UPDATE_SUCCESSFULLY, null),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/houses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> deleteHouse(@PathVariable Long id) {
        HouseEntity house = this.houseService.findById(id);

        if (house == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND);
        }

        this.houseService.delete(id);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, DELETE_THE_HOUSE_SUCCESSFULLY, null),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/house/orderOfUser/",method = RequestMethod.GET)
    @PreAuthorize("hasRole('HOST')")
    public  ResponseEntity<ResponseMessage> getHouseOrderByUser(){
        long userId = getCurrentUser().getId();
        List<UserOrderListOfHost> userOrderListsOfHost = this.orderHouseService.userOrderListOfHost(userId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, LIST_ALL_ORDER,userOrderListsOfHost),HttpStatus.OK);
    }


}
