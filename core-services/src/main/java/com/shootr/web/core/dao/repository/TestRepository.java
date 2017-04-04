package com.shootr.web.core.dao.repository;

import com.shootr.web.core.dao.domain.Test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {

    List<Test> findByNameIgnoreCaseContainingAndDeletedIsNullOrderByNameDesc(String name);

}
