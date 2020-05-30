package com.ez.userdata.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDataRepository extends CrudRepository<UserDataEntity, Long> {
    UserDataEntity findByInfoId(String infoId);

    @Transactional
    void deleteByInfoId(String infoId);

    @Query(value="SELECT userdata FROM UserDataEntity userdata WHERE userdata.userId = ?1")
    List<UserDataEntity> findByUserId(String infoId);
}