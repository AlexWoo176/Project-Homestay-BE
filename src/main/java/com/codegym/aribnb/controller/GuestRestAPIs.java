package com.codegym.aribnb.controller;


import com.codegym.aribnb.message.response.OrderDetail;
import com.codegym.aribnb.message.response.ResponseMessage;
import com.codegym.aribnb.message.response.UserOrderList;
import com.codegym.aribnb.model.*;
import com.codegym.aribnb.security.services.UserPrinciple;
import com.codegym.aribnb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/me")
public class GuestRestAPIs {
    public static final String NOT_FOUND_DATA = "Fail. Not found data";
    public static final String SUCCESSFULLY_GET_LIST_ORDERS_THAT_WAS_BOOKED_BY_GUEST = "Successfully. Get list orders that was booked by guest";
    public static final String SUCCESSFULLY_GET_THE_HOUSE_OF_ORDER = "Successfully. Get the house of order";
    public static final String SUCCESSFULLY_GET_DETAIL_ORDER_THAT_WAS_BOOKED_BY_GUEST = "Successfully. Get detail order that was booked by guest";
    public static final String CANNOT_CANCEL_THE_ORDER = "Cannot cancel the order";
    public static final String CONFIRM_ORDER_CANCEL = "Confirm order cancel";
    public static final String COMMENT_SUCCESSFUL = "Comment Successful";
    public static final String YOU_CAN_RATE_ONE = "You can rate one";
    public static final String RATE_SUCCESSFUL = "Rate Successful";
    public static final String YOU_HAVE_NOT_RATE_THIS_HOUSE = "You have not rate this house!";
    public static final String HOUSE_RATE = "House Rate";
    @Autowired
    private IHouseService houseService;

    @Autowired
    private IOrderHouseService orderHouseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IRateService rateService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('HOST')")
    public ResponseEntity<ResponseMessage> listOrderOfGuest() {
        long userId = getCurrentUser().getId();
        List<UserOrderList> userOrderLists = this.orderHouseService.userOrderLists(userId);

        if (userOrderLists.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, NOT_FOUND_DATA, null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_ORDERS_THAT_WAS_BOOKED_BY_GUEST, userOrderLists),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}/house-of-order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> getHouseOfOrder(@PathVariable long id) {
        OrderHouse orderHouse = this.orderHouseService.findById(id);

        if (orderHouse == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false,NOT_FOUND_DATA , null),
                    HttpStatus.OK);
        }

        HouseEntity house = orderHouse.getHouse();

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_THE_HOUSE_OF_ORDER, house),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> getDetailOrder(@PathVariable Long id) {
        long userId = getCurrentUser().getId();
        long orderId = id;
        OrderDetail orderDetail = this.orderHouseService.findById(userId,orderId);

        if (orderDetail == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, NOT_FOUND_DATA, null),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_DETAIL_ORDER_THAT_WAS_BOOKED_BY_GUEST, orderDetail),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteOrderHouse(@PathVariable Long id) {
        OrderHouse orderHouse = this.orderHouseService.findById(id);
        Date checkin = orderHouse.getCheckin();
        Date now = new Date();
        int day = 86400 * 1000;
        double nowToCheckinByDay = (double) (checkin.getTime() - now.getTime()) / day;
        if (nowToCheckinByDay < 1.0) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, CANNOT_CANCEL_THE_ORDER, null),
                    HttpStatus.OK);
        }
        orderHouse.setStatusOrder(StatusOrder.CANCELED);
        this.orderHouseService.update(orderHouse);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, CONFIRM_ORDER_CANCEL, null),
                HttpStatus.OK);
    }

    @PostMapping("{houseId}/comments")
    public ResponseEntity<ResponseMessage> createComment(@RequestBody Comment comment, @PathVariable Long houseId) {
        comment.setUser(this.userService.findById(getCurrentUser().getId()));
        HouseEntity house = houseService.findById(houseId);
        comment.setHouse(house);
        this.commentService.create(comment);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, COMMENT_SUCCESSFUL, null),
                HttpStatus.CREATED);
    }

    @PostMapping("/rates")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseMessage> createRate(@RequestBody Rate rate) {
        rate.setUser(this.userService.findById(getCurrentUser().getId()));
        if (this.rateService.existsRateByUserIdAndHouseId(rate.getUser().getId(), rate.getHouse().getId() ) ){
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(true, YOU_CAN_RATE_ONE, null),
                    HttpStatus.CREATED);
        }
        this.rateService.create(rate);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, RATE_SUCCESSFUL, null),
                HttpStatus.CREATED);
    }

    @GetMapping("/rates/{houseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseMessage> getRateByUserIdAndHouseId(@PathVariable Long houseId){
        Rate rate = this.rateService.findByUserIdAndHouseId(getCurrentUser().getId(), houseId);
        if(rate == null){
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(false, YOU_HAVE_NOT_RATE_THIS_HOUSE, null), HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, HOUSE_RATE, rate), HttpStatus.OK);
    }

}
