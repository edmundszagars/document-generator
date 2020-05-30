package com.ez.users.ui.controllers;

import com.ez.users.services.UserService;
import com.ez.users.shared.UserDto;
import com.ez.users.ui.models.CreateUserModel;
import com.ez.users.ui.models.UserResponseModel;
import com.ez.users.ui.models.userdata.CreateUserDataModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment environment;
    private final RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public UsersController(Environment environment, UserService userService, RestTemplate restTemplate) {
        this.environment = environment;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/status")
    public String status() {
        return "Works on port: " + environment.getProperty("local.server.port");
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseModel> createUser(@RequestBody @Valid CreateUserModel userModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userModel, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);

        UserResponseModel response = modelMapper.map(createdUser, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("{userId}/create-data")
    public CreateUserDataModel createUserData(@PathVariable String userId,
                                              @RequestBody CreateUserDataModel userData) {
        String theUrl = "http://USER-DATA/user-data/" + userId;
        HttpEntity<CreateUserDataModel> requestEntity = new HttpEntity<>(userData);
        ResponseEntity<CreateUserDataModel> response =
                restTemplate.exchange(theUrl,
                        HttpMethod.POST,
                        requestEntity,
                        CreateUserDataModel.class);

        return response.getBody();
    }

    @GetMapping("/data-status")
    public String getStatusFromUserDataService() {
        String theUrl = "http://USER-DATA/user-data/status";
        ResponseEntity<String> response =
                restTemplate.exchange(theUrl,
                        HttpMethod.GET,
                        null,
                        String.class);

        return response.getBody();
    }
}
