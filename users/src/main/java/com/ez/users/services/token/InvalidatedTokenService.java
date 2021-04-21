package com.ez.users.services.token;

import com.ez.users.data.token.InvalidatedTokenEntity;
import com.ez.users.data.token.InvalidatedTokenRepository;
import com.ez.users.shared.UserLogoutDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class InvalidatedTokenService {
    private ModelMapper modelMapper;
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    public InvalidatedTokenService(InvalidatedTokenRepository invalidatedTokenRepository, ModelMapper modelMapper) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
        this.modelMapper = modelMapper;
    }

    public UserLogoutDto logoutUser() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];

        InvalidatedTokenEntity invalidatedTokenEntity = new InvalidatedTokenEntity();
        invalidatedTokenEntity.setToken(token);
        invalidatedTokenEntity.setInvalidationTime(new Date());
        invalidatedTokenRepository.save(invalidatedTokenEntity);
        return new UserLogoutDto();
    }
}

