package com.starter.base.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DeptControllerTest.class, LoginControllerTest.class,
        MenuControllerTest.class, RoleControllerTest.class, UserControllerTest.class})
public class AllTests {
}
