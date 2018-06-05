package com.allyes.minimec.test;

import com.allyes.minimec.WebStartApplication;
import com.allyes.minimec.admin.service.sys.AdminUserService;
import com.allyes.minimec.domain.model.sys.AdminUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by liuwei
 * date: 16/8/18
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= WebStartApplication.class)
public class ApplicationTests {


    @Autowired
    AdminUserService adminUserService;


    @Before
    public void setUp() {

    }

    @Test
    public void test() throws Exception {

        List<AdminUser> list = adminUserService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(list);
        log.info(content);


    }

}