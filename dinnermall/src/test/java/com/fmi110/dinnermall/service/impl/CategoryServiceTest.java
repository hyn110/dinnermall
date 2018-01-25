package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.service.ICategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 16:32
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryServiceTest {

    @Autowired
    ICategoryService service;

    @Test
    public void findOne() throws Exception {
        service.findOne(null);
    }

}