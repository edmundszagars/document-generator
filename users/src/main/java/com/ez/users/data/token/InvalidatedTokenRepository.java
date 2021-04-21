package com.ez.users.data.token;

import org.springframework.data.repository.CrudRepository;

public interface InvalidatedTokenRepository extends CrudRepository<InvalidatedTokenEntity,Long> {
}
