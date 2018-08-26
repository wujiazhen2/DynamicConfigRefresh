package com.qworldr.test;

import com.qworldr.test.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Demo {
    @Autowired
    private TestConfig testContext;

    @Test
    public void  test1(){
        System.out.println(testContext);
    }
}
