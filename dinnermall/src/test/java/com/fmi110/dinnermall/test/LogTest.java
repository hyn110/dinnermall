package com.fmi110.dinnermall.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/24 16:56
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class LogTest {
    @Test
    public void test(){
        log.info("info 日志");
        log.error("erro 日志");
    }
}
