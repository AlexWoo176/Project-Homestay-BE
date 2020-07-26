package com.codegym.aribnb.controller;

import com.codegym.aribnb.message.response.*;
import com.codegym.aribnb.model.*;
import com.codegym.aribnb.security.services.UserPrinciple;
import com.codegym.aribnb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HouseRestAPIs {
    public static final String FAIL_NOT_FOUND_DATA = "Fail. Not found data";
    public static final String SUCCESSFULLY_GET_LIST_ALL_HOUSE = "Successfully. Get list all house";
    public static final String SUCCESSFULLY_GET_DETAIL_HOUSE = "Successfully. Get detail house";
    public static final String ANOTHER_CUSTOMER_ALREADY_BOOKED_THIS_DATE_PLEASE_TRY_TO_BOOK_ANOTHER_DATE = "Another customer already booked this date. Please try to book another date.";
    public static final String BOOKING_SUCCESS = "Booking success!";
    public static final String SUCCESSFULLY_BUT_NOT_FOUND_DATA = "Successfully but not found data";
    public static final String SUCCESSFULLY_GET_LIST_STATUS_HOUSES = "Successfully. Get list status houses";
    public static final String LIST_ALL_ORDER = "list all order";
    public static final String SUCCESSFULLY_GET_LIST_ALL_CATEGORY = "Successfully. Get list all category";
    public static final String SUCCESSFULLY_GET_LIST_COMMENT_THAT_WAS_BOOKED_BY_GUEST = "Successfully. Get list comment that was booked by guest";
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

    @Autowired
    private IStatusHouseService statusHouseService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/houses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> listAllHouse(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<HouseList> houses = this.houseService.getListHouse(1, 2);

        if (houses.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND_DATA, null),
                    HttpStatus.OK);
        }
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_ALL_HOUSE, houses),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/houses2/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> getHouseNative(@PathVariable Long id) {
        HouseDetail house = this.houseService.getHouseDetailById(id);
        if (house == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND_DATA, null),
                    HttpStatus.OK);
        }
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_DETAIL_HOUSE, house),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/houses/{id}/booking", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> bookingHouse(@PathVariable Long id, @RequestBody OrderHouse orderHouse) {
        boolean isBooked =
                orderHouseService.existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByEndDateGreaterThanEqualAndEndDateLessThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByStartDateGreaterThanEqualAndEndDateLessThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByStartDateGreaterThanEqualAndStartDateLessThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByStartDateLessThanEqualAndEndDateGreaterThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id);
        if (isBooked) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, ANOTHER_CUSTOMER_ALREADY_BOOKED_THIS_DATE_PLEASE_TRY_TO_BOOK_ANOTHER_DATE, null),
                    HttpStatus.OK);
        }
        HouseEntity house = houseService.findById(id);
        orderHouse.setHouse(house);
        User tenant = userService.findById(getCurrentUser().getId());
        orderHouse.setTenant(tenant);
        orderHouse.setStatusOrder(StatusOrder.PROCESSING);
        orderHouseService.create(orderHouse);
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, BOOKING_SUCCESS, null),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/house/all-user-order", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> allUserOder() {
        List<OrderHouse> orderHouses = orderHouseService.findAll();
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, LIST_ALL_ORDER, orderHouses), HttpStatus.OK);
    }

    @RequestMapping(value = "/statusHouses/{houseId}", method = RequestMethod.GET)
    private ResponseEntity<ResponseMessage> listStatusHouse(@PathVariable Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseService.findAllByHouseId(houseId);

        if (statusHouses.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(true, SUCCESSFULLY_BUT_NOT_FOUND_DATA, null),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_STATUS_HOUSES, statusHouses),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/category-list", method = RequestMethod.GET)
    private ResponseEntity<ResponseMessage> listCategoryHouse() {
        List<CategoryList> categoryLists = this.houseService.getListCategory();

        if (categoryLists.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND_DATA, null),
                    HttpStatus.OK);
        }
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_ALL_CATEGORY, categoryLists),
                HttpStatus.OK);

    }

    @RequestMapping(value = "/comments/{houseId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> listCommentsByHouseId(@PathVariable Long houseId) {
        List<CommentList> commentLists = this.commentService.findAllByHouseId(houseId);

        if (commentLists.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND_DATA, null),
                    HttpStatus.OK);
        }
        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_COMMENT_THAT_WAS_BOOKED_BY_GUEST, commentLists),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/rates/{houseId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> listRatesByHouseId(@PathVariable Long houseId) {
        List<Rate> rates = this.rateService.findAllByHouseId(houseId);

        if (rates.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_NOT_FOUND_DATA, null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, SUCCESSFULLY_GET_LIST_COMMENT_THAT_WAS_BOOKED_BY_GUEST, rates),
                HttpStatus.OK);
    }

}
