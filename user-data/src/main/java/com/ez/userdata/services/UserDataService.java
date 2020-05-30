package com.ez.userdata.services;

import com.ez.userdata.data.UserDataEntity;
import com.ez.userdata.data.UserDataRepository;
import com.ez.userdata.shared.UserDataDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDataService {

    private UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public UserDataDto createUserData(UserDataDto userDataDto, String userId) {
        userDataDto.setUserId(userId);
        userDataDto.setInfoId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDataEntity userEntity = modelMapper.map(userDataDto, UserDataEntity.class);
        userDataRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDataDto.class);
    }

    public UserDataDto findByInfoId(String id) {
        UserDataEntity userDataEntity = userDataRepository.findByInfoId(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userDataEntity, UserDataDto.class);
    }

    public List<UserDataDto> findByUserId(String userId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<UserDataDto> userDataDtoList = new ArrayList<>();
        userDataRepository.findByUserId(userId).forEach(userDataEntity -> {
            UserDataDto userDto = modelMapper.map(userDataEntity, UserDataDto.class);
            userDataDtoList.add(userDto);
        });
        return userDataDtoList;
    }

    public ResponseEntity<Map<String, String>> deleteData(String id) {
        userDataRepository.deleteByInfoId(id);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Content successfully deleted");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMessage);
    }
}
