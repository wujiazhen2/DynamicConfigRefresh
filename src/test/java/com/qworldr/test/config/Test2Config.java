package com.qworldr.test.config;

import com.qworldr.annotation.Config;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Config
public class Test2Config {
    private Date time;
    private Integer code;
    private String test;

    public Date getTime() {
        return time;
    }

    public Integer getCode() {
        return code;
    }

    public String getTest() {
        return test;
    }

    @Override
    public String toString() {
        return "Test2Config{" +
                "time=" + time +
                ", code=" + code +
                ", test='" + test + '\'' +
                '}';
    }
}
