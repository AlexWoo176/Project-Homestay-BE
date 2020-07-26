package com.codegym.aribnb.controller;

import com.codegym.aribnb.message.request.LoginForm;
import com.codegym.aribnb.message.request.SignUpForm;
import com.codegym.aribnb.message.response.JwtResponse;
import com.codegym.aribnb.message.response.ResponseMessage;
import com.codegym.aribnb.model.Role;
import com.codegym.aribnb.model.RoleName;
import com.codegym.aribnb.model.User;
import com.codegym.aribnb.repository.IRoleRepository;
import com.codegym.aribnb.repository.IUserRepository;
import com.codegym.aribnb.security.jwt.JwtProvider;
import com.codegym.aribnb.security.services.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
    public static final String FAIL_USERNAME_IS_ALREADY_TOKEN = "Fail -> Username is already token!";
    public static final String SUCCESS = "Success";
    public static final String FAIL_EMAIL_IS_ALREADY_IN_USE = "Fail -> Email is already in use!";
    public static final String FAIL_CAUSE_USER_ROLE_NOT_FIND = "Fail! -> Cause: User Role not find.";
    public static final String USER_REGISTERED_WITH_ROLE_HOST_SUCCESSFULLY = "User registered with ROLE_HOST successfully!";
    public static final String FAIL_USERNAME_ALREADY_EXISTS = "Fail -> Username already exists!";
    public static final String FAIL_EMAIL_ALREADY_USES = "Fail -> Email already uses!";
    public static final String USER_REGISTERED_WITH_ROLE_GUEST_SUCCESSFULLY = "User registered with ROLE_GUEST successfully!";
    public static final String FAIL_CAUSE_USER_ROLE_NOT_FIND1 = "Fail! -> Cause: User Role not find.";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            UserPrinciple user = (UserPrinciple) userDetails;
            Long id = user.getId();

            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(true, SUCCESS + loginRequest.getPassword(), new JwtResponse(id, jwt, userDetails.getUsername(), userDetails.getAuthorities())),
                    HttpStatus.OK);
        } catch (DisabledException e) {
            e.printStackTrace();
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    //signup with ROLE_HOST
    @RequestMapping(value = "/host/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> registerHost(@Valid @RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_USERNAME_IS_ALREADY_TOKEN, null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_EMAIL_IS_ALREADY_IN_USE, null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(RoleName.ROLE_HOST)
                .orElseThrow(() -> new RuntimeException(FAIL_CAUSE_USER_ROLE_NOT_FIND));
        roles.add(adminRole);
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, USER_REGISTERED_WITH_ROLE_HOST_SUCCESSFULLY, null),
                HttpStatus.OK);
    }

    //signup with ROLE_GUEST
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_USERNAME_ALREADY_EXISTS, null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, FAIL_EMAIL_ALREADY_USES, null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException(FAIL_CAUSE_USER_ROLE_NOT_FIND1));
        roles.add(userRole);
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, USER_REGISTERED_WITH_ROLE_GUEST_SUCCESSFULLY, null),
                HttpStatus.OK);
    }
}
