package com.ez.users.data.token;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "invalidated_toikens")
public class InvalidatedTokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "invalidation_time")
    private Date invalidationTime;

    @Column(name = "token")
    private String token;
}
