package com.ez.users.services.token;

import com.ez.users.data.token.InvalidatedTokenEntity;
import com.ez.users.data.token.InvalidatedTokenRepository;
import com.ez.users.shared.TokenDto;
import com.ez.users.shared.UserLogoutDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class InvalidatedTokenService {
    private ModelMapper modelMapper;
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    public InvalidatedTokenService(InvalidatedTokenRepository invalidatedTokenRepository, ModelMapper modelMapper) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity logoutUser() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];

        InvalidatedTokenEntity invalidatedTokenEntity = new InvalidatedTokenEntity();
        invalidatedTokenEntity.setToken(token);
        invalidatedTokenEntity.setInvalidationTime(new Date());
        invalidatedTokenRepository.save(invalidatedTokenEntity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> getAllInvalidatedTokens(String token) {
        List<InvalidatedTokenEntity> invalidatedTokenEntities = (List<InvalidatedTokenEntity>) invalidatedTokenRepository.findAll();
        List<TokenDto> invalidTokens = new ArrayList<>();

        invalidatedTokenEntities.forEach(ivt -> {
                    TokenDto tokenDto = new TokenDto();
                    modelMapper.map(ivt, tokenDto);
                    invalidTokens.add(tokenDto);
                }
        );
        for (TokenDto t : invalidTokens) {
            if (t.getToken().equals(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

