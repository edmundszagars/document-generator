package com.ez.users.tests;

import com.ez.users.cases.RegisterUserCase;
import com.ez.users.services.user.UserService;
import com.ez.users.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private RegisterUserCase registerUserCase;

    @BeforeEach
    void initUseCase() {
        registerUserCase = new RegisterUserCase(userService);
    }

    @Test
    public void saveNewUser() {
        UserDto userDto = new UserDto("Jon", "Doe", "mail@bail.com", "safe", UUID.randomUUID().toString(), "");
        when(registerUserCase.registerNewUser(any(UserDto.class))).then(returnsFirstArg());
        UserDto savedUser = registerUserCase.registerNewUser(userDto);
        assertThat(savedUser.getUserId()).isNotNull();
    }
}
