package controller;

import com.panpawelw.controller.UserGroupsAdminPanel;
import mockDAOs.MockUserGroupDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UserGroupsAdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private UserGroupsAdminPanel userGroupsAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        userGroupsAdminPanel = new UserGroupsAdminPanel();
    }

    @Test
    public void userGroupsAdminPanelForwardTest() throws Exception {
        userGroupsAdminPanel.setUserGroupDAO(new MockUserGroupDAO());
        userGroupsAdminPanel.init(config);
        userGroupsAdminPanel.doGet(request, response);
        assertEquals("/jsp/usergroupsadminview.jsp", response.getForwardedUrl());
    }
}
