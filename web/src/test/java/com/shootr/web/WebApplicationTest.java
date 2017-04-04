package com.shootr.web;

import com.shootr.web.core.config.Profiles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({Profiles.SPRING_PROFILE_CI})
public class WebApplicationTest {

    @Test
    public void contextLoads() {
    }

}
