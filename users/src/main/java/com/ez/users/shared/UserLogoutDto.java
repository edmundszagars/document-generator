package com.ez.users.shared;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogoutDto {
    private Date invalidationTime;
    private String token;
}
