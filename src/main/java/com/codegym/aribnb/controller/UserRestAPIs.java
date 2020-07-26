package com.codegym.aribnb.controller;

import com.codegym.aribnb.message.response.JwtResponse;
import com.codegym.aribnb.message.response.ResponseMessage;
import com.codegym.aribnb.message.response.UserInformation;
import com.codegym.aribnb.model.User;
import com.codegym.aribnb.security.jwt.JwtProvider;
import com.codegym.aribnb.security.services.UserPrinciple;
import com.codegym.aribnb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserRestAPIs {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtProvider jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> listAllUser() {
        List<User> users = this.userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/updateCurrent", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User currentUser = userService.findById(getCurrentUser().getId());
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        currentUser.setName(user.getName());
        userService.update(currentUser);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<UserInformation> getUserById() {
        long userId = getCurrentUser().getId();
        UserInformation user = userService.findByIdCurrent(userId);
        return new ResponseEntity<UserInformation>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/confirmPassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> comparePassword(@RequestBody String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(getCurrentUser().getUsername(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenUtil.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserPrinciple user = (UserPrinciple) userDetails;
            Long id = user.getId();
            return new  ResponseEntity<ResponseMessage>(
                    new ResponseMessage(true,"Confirm Success",new JwtResponse(id, jwt,
                            userDetails.getUsername(), userDetails.getAuthorities())),
                    HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(false, "confirm fail", null), HttpStatus.NOT_FOUND);
        }
    }
}
