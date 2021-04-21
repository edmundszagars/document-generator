package com.ez.users.cases;

import com.ez.users.services.user.UserService;
import com.ez.users.shared.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserCase {

    private final UserService userService;

    public UserDto registerNewUser(UserDto userDto) {
        return userService.createUser(userDto);
    }
}
