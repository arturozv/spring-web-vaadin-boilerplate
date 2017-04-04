package com.shootr.web.core.service;

import com.shootr.web.core.dao.domain.Test;
import com.shootr.web.core.dao.repository.TestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

@Service
public class TestServiceImpl implements TestService {

    private final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Inject
    private TestRepository testRepository;

    public Test save(Test test) {
        log.debug("Request to save entity: {}", test);
        return test.getId() != null ? testRepository.save(test) : testRepository.insert(test);
    }

    @Transactional(readOnly = true)
    public List<Test> findAll() {
        log.debug("Request to get all entities");
        return testRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Test findOne(String id) {
        log.debug("Request to get by id: {}", id);
        return testRepository.findOne(id);
    }

    public void delete(String id) {
        log.debug("Request to delete entity: {}", id);
        testRepository.delete(id);
    }

    @Override
    public List<Test> findByName(String name) {
        log.debug("Request to get Test by name : {}", name);
        return testRepository.findByNameIgnoreCaseContainingAndDeletedIsNullOrderByNameDesc(name);
    }
}
