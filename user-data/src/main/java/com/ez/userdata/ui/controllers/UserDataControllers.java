package com.ez.userdata.ui.controllers;

import com.ez.userdata.services.UserDataService;
import com.ez.userdata.shared.UserDataDto;
import com.ez.userdata.ui.modles.UserDataCreateModel;
import com.ez.userdata.ui.modles.UserDataCreatedResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user-data")
public class UserDataControllers {

    @Autowired
    private Environment environment;

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/status")
    public String status() {
        return "User data service works on port: " + environment.getProperty("local.server.port");
    }

    @PostMapping("/create")
    public ResponseEntity<UserDataCreatedResponseModel> createData(CurrentUser currentUser,
                                                                   @RequestBody UserDataCreateModel userDataCreateModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDataDto userDto = modelMapper.map(userDataCreateModel, UserDataDto.class);
        UserDataDto createdUser = userDataService.createUserData(userDto, currentUser.sub);

        UserDataCreatedResponseModel response = modelMapper.map(createdUser, UserDataCreatedResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<UserDataDto>>> gatAllCv(CurrentUser currentUser) {
        Map<String, List<UserDataDto>> map = new HashMap<>();
        map.put("userData", userDataService.findByUserId(currentUser.sub));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataDto> getUserData(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userDataService.findByInfoId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String id) {
        return userDataService.deleteData(id);
    }
}
