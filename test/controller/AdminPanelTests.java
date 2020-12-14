package controller;

import com.panpawelw.controller.AdminPanel;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class AdminPanelTests {

    @Test
    public void adminPanelForwardTest() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.init(new MockServletConfig());
        adminPanel.doGet(new MockHttpServletRequest(), response);
        assertEquals("/jsp/panelview.jsp", response.getForwardedUrl());
    }
}
