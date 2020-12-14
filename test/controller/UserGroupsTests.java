package controller;

import com.panpawelw.controller.UserGroups;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UserGroupsTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserGroups userGroups;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userGroups = new UserGroups();
        userGroups.setUserGroupDAO(new MockUserGroupDAO());
        userGroups.init(new MockServletConfig());
    }

    @Test
    public void UserGroupsForwardTest() throws Exception {
        userGroups.doGet(request, response);
        assertEquals("/jsp/usergroupsview.jsp", response.getForwardedUrl());
    }
}
