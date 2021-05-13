package com.book.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(TestApplicationContext.class);


    @Test
    public void testAppInitialisation() {
        log.debug("Application initialised");

    }
}
