package controller;

import com.panpawelw.controller.AdminPanel;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class AdminPanelTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private AdminPanel adminPanel;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        adminPanel = new AdminPanel();
    }

    @Test
    public void adminPanelForwardTest() throws Exception {
        adminPanel.init(config);
        adminPanel.doGet(request, response);
        assertEquals("/jsp/panelview.jsp", response.getForwardedUrl());
    }
}