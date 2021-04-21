package com.ez.users.ui.controllers;

import com.ez.users.services.token.InvalidatedTokenService;
import com.ez.users.services.user.UserService;
import com.ez.users.shared.UserDto;
import com.ez.users.ui.models.CreateUserModel;
import com.ez.users.ui.models.UserResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final InvalidatedTokenService invalidatedTokenService;

    private final ModelMapper mapper;

    @Autowired
    public UsersController(UserService userService, InvalidatedTokenService invalidatedTokenService, ModelMapper mapper) {
        this.userService = userService;
        this.invalidatedTokenService = invalidatedTokenService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseModel> createUser(@RequestBody @Valid CreateUserModel userModel) {
        UserDto userDto = mapper.map(userModel, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);

        UserResponseModel response = mapper.map(createdUser, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        invalidatedTokenService.logoutUser();
        return ResponseEntity.status(HttpStatus.OK).body("Logged out!");
    }

    @GetMapping("/invalidated-token")
    public ResponseEntity<Void> getAllInvalidTokens(@RequestParam String token) {
        log.info("This is your token: " + token);
        return invalidatedTokenService.getAllInvalidatedTokens(token);
    }
}