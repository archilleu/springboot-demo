package com.hoya.admin;

import com.hoya.admin.dao.sys.SysTestsSuites;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SysTestsSuites.class})
public class TestsSuite {
}
