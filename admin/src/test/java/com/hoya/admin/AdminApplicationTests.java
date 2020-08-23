package com.hoya.admin;

import com.hoya.admin.server.sys.SysUserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {

	@Autowired
    private SysUserService sysUserService;
}
