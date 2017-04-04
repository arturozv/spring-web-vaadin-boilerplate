package com.shootr.web.core.service;

import com.shootr.web.core.dao.domain.Test;

import java.util.List;

public interface TestService {

    Test save(Test test);

    List<Test> findAll();

    Test findOne(String id);

    void delete(String id);

    List<Test> findByName(String name);
}
