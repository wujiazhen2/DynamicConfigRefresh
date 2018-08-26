package com.qworldr.test;

import com.qworldr.test.config.Test2Config;
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

    @Autowired
    private Test2Config test2Config;

    @Test
    public void  test1() throws InterruptedException {
        System.out.println(testContext);
        System.out.println(test2Config);
        //测试文件动态更新，更改test-classes里面的配置文件,或更改test下的编译一次
        Thread.sleep(30000);
        System.out.println(testContext);
        System.out.println(test2Config);
    }
}
