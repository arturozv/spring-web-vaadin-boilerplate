package com.shootr.web.core.dao.repository;

import com.shootr.web.core.dao.domain.RememberUser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RememberUserRepository extends MongoRepository<RememberUser, String> {

    RememberUser findByCookie(String cookie);

}
