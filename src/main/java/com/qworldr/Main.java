package com.qworldr;

import com.qworldr.config.TestConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

         public static void main(String[] args){
             ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
             ((ClassPathXmlApplicationContext) applicationContext).start();
             TestConfig testConfig = (TestConfig)applicationContext.getBean("testConfig");
             System.out.println(testConfig);
         }
}
