package controller;

import com.panpawelw.controller.UserGroupsAdminDelete;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UserGroupsAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private UserGroupsAdminDelete userGroupsAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        userGroupsAdminDelete = new UserGroupsAdminDelete();
    }

    @Test
    public void UserGroupsAdminDeleteForwardTest() throws Exception {
        userGroupsAdminDelete.setUserGroupDAO(new MockUserGroupDAO());
        request.setParameter("id", "0");
        userGroupsAdminDelete.init(config);
        userGroupsAdminDelete.doGet(request, response);
        assertEquals("/groupsadminpanel", response.getForwardedUrl());
    }
}
