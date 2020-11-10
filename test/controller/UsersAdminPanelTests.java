package controller;

import com.panpawelw.controller.UsersAdminPanel;
import mockDAOs.MockUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UsersAdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private UsersAdminPanel usersAdminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        usersAdminPanel = new UsersAdminPanel();
    }

    @Test
    public void usersAdminPanelForwardTest() throws Exception {
        usersAdminPanel.setUserDAO(new MockUserDAO());
        usersAdminPanel.init(config);
        usersAdminPanel.doGet(request, response);
        assertEquals("/jsp/usersadminview.jsp", response.getForwardedUrl());
    }
}
